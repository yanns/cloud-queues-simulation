name := """cloud-queues-simulation"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0-M4",
  "com.typesafe" % "config" % "1.2.1",
  "com.github.kxbmap" %% "configs" % "0.2.3",
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

spray.revolver.RevolverPlugin.Revolver.settings
