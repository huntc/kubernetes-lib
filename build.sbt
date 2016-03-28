organization := "com.lightbend.lagom"

name := "kubernetes-lib"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.lightbend.lagom" %% "lagom-javadsl-api" % "1.0.0-M1",
  "com.typesafe.play"   %% "play-ws"           % "2.5.0"
)