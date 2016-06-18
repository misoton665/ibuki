name := """ibuki"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
  "com.typesafe.play" % "play-jdbc_2.11" % "2.5.3",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

routesGenerator := InjectedRoutesGenerator

