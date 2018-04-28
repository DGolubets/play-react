import Dependencies._

name := "play-react"
organization := "ru.dgolubets"

scalaVersion := "2.12.6"
crossScalaVersions := List("2.11.11", "2.12.6")
releaseCrossBuild := true

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers += Resolver.bintrayRepo("dgolubets", "releases")

libraryDependencies ++= Seq(
  DGolubets.reactjsServer,
  Play.play,
  typesafeConfig,
  scalaLogging,
  Circe.core,
  Circe.generic,

  // test
  scalaTest % Test,
  scalaMock % Test,
  logback % Test
)

// publishing
bintrayRepository := "releases"
bintrayOrganization in bintray := Some("dgolubets")
bintrayPackageLabels := Seq("play", "react")
licenses += ("MIT", url("https://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/DGolubets/play-react"))
publishMavenStyle := true
publishArtifact in Test := false
developers := List(Developer(
  "dgolubets",
  "Dmitry Golubets",
  "dgolubets@gmail.com",
  url("https://github.com/DGolubets")))