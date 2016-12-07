name := "kinesis_stream"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.0",
  "com.amazonaws" % "aws-java-sdk" % "1.11.52",
//  "com.amazonaws" % "aws-java-sdk-core" % "1.11.51",
  "com.amazonaws" % "amazon-kinesis-client" % "1.7.1",
  "com.amazonaws" % "amazon-kinesis-producer" % "0.12.1",
  "com.aerospike" % "aerospike-client" % "latest.integration",
  "com.github.nscala-time" %% "nscala-time" % "2.8.0",
  "org.json4s" %% "json4s-jackson" % "latest.integration",
  "com.typesafe" % "config" % "1.3.1"
)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.7" % "test" ,
  "org.specs2" %% "specs2-mock" % "3.7" % "test"
)
