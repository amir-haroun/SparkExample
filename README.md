# Spark example
This is a basic scala spark project, it uses spark 4 and scala 2.13.
 - It reads to files (JSON/CSV) from the data directory, 
 - Does some calculation for every vehicle 
 - Executes a join to retreive the driver's name
 - Writes the result in the outupt directory

## Execute the code using sbt
```bash
sbt compile
sbt run
```
## Build the fat Jar
```bash
sbt assembly
```
This command will generate a Jar under target/scala-2.13, this jar can be shipped and executed on a spark cluster.
