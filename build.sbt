name := "akka-testing"

version := "1.0"

scalaVersion := "2.12.1"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-actor_2.12
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.5.4"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.4"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.10"

mainClass in Compile := some("MrHttp")

        