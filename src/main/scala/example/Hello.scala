package example
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SaveMode

object Hello extends App {
  val spark = SparkSession.builder
    .master("local")
    .appName("Simple Application")
    .getOrCreate()
  import spark.implicits._
  val df = spark.read.format("json").load("data/data.json")
  val drivers =
    spark.read.option("header", "true").format("csv").load("data/veh.csv")
  val result = df
    .filter($"messageType".isin("start", "stop"))
    .select("vin", "messageType", "payload.mileage")
    .groupBy("vin")
    .agg(
      first(
        when($"messageType" === "start", $"mileage"),
        ignoreNulls = true
      )
        .as("start_mileage"),
      first(
        when($"messageType" === "stop", $"mileage"),
        ignoreNulls = true
      )
        .as("stop_mileage")
    )
    .withColumn("mileage_difference", $"stop_mileage" - $"start_mileage")
    .drop("start_mileage", "stop_mileage")
  result
    .join(drivers, Seq("vin"))
    .write
    .mode(SaveMode.Overwrite)
    .partitionBy("vin")
    .format("json")
    .save("output")
}
