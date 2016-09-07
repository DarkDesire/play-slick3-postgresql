name := "SimpleRestMicroservice"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq (
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "org.postgresql" % "postgresql" % "9.4.1209",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
  specs2 % Test
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)