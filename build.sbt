import Dependencies._

ThisBuild / scalaVersion     := "2.13.16"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"
val sparkVersion = "4.0.1"
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion// % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion// % "provided"
lazy val root = (project in file("."))
  .settings(
    name := "mySparkProject",
    libraryDependencies += munit % Test
  )
assembly / mainClass := Some("example.Hello")
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}