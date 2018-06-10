import sbt._

object Dependencies {

  object DGolubets {
    val reactjsServer = "ru.dgolubets" %% "reactjs-server" % "0.2.1"
  }

  object Play {
    private val version = "2.6.13"
    val play = "com.typesafe.play" %% "play" % version
  }

  object Circe {
    private val version = "0.9.1"

    val core = "io.circe" %% "circe-core" % version
    val generic = "io.circe" %% "circe-generic" % version
    val parser = "io.circe" %% "circe-parser" % version
  }

  val typesafeConfig = "com.typesafe" % "config" % "1.3.3"
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"
  val scalaMock = "org.scalamock" %% "scalamock" % "4.1.0"
  val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
}
