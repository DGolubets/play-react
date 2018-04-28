package ru.dgolubets.play.react

import java.io.File

import scala.collection.JavaConverters._
import scala.util.Try

import com.typesafe.config.{Config, ConfigFactory}

import ru.dgolubets.reactjs.server.{RenderServerSettings, ScriptSource, WatchSettings}

case class RenderConfiguration(serverSettings: RenderServerSettings,
                               defaultRenderFunction: RenderFunction)

object RenderConfiguration {

  def load(): RenderConfiguration = {
    load(ConfigFactory.load().getConfig("ru.dgolubets.play.react"))
  }

  def load(config: Config): RenderConfiguration = {
    RenderConfiguration(
      loadRenderServerSettings(config.getConfig("render-server")),
      RenderFunction(config.getString("default-render-function")))
  }

  private def loadRenderServerSettings(config: Config): RenderServerSettings = {
    RenderServerSettings(
      config.getStringList("sources").asScala.map(parseSource),
      Try(config.getString("watch.root")).toOption.map(root => WatchSettings(new File(root)))
    )
  }

  private def parseSource(s: String): ScriptSource = {
    val resourcePrefix = "resource!"
    val filePrefix = "file!"

    if (s.startsWith(resourcePrefix)) {
      ScriptSource.fromResource(s.substring(resourcePrefix.length))
    }
    else if (s.startsWith(filePrefix)) {
      ScriptSource.fromFile(new File(s.substring(filePrefix.length)))
    }
    else {
      ScriptSource.fromFile(new File(s))
    }
  }
}

