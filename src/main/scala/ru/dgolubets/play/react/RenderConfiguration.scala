package ru.dgolubets.play.react

import java.io.File

import scala.collection.JavaConverters._
import scala.util.Try

import com.typesafe.config.{Config, ConfigFactory}

import ru.dgolubets.reactjs.server.{RenderServerSettings, ScriptSource, WatchSettings}

case class RenderConfiguration(serverSettings: RenderServerSettings,
                               defaultRenderFunction: RenderFunction,
                               enabled: Boolean)

object RenderConfiguration {

  def load(): RenderConfiguration = {
    load(ConfigFactory.load().getConfig("ru.dgolubets.play.react"))
  }

  def load(config: Config): RenderConfiguration = {
    RenderConfiguration(
      loadRenderServerSettings(config.getConfig("render-server")),
      RenderFunction(config.getString("default-render-function")),
      config.getBoolean("render-server.enabled"))
  }

  private def loadRenderServerSettings(config: Config): RenderServerSettings = {
    var nInstances = config.getInt("instances")
    if(nInstances == 0){
      nInstances = Runtime.getRuntime.availableProcessors
    }
    RenderServerSettings(
      sources = config.getStringList("sources").asScala.map(parseSource),
      watch = Try(config.getString("watch.root")).toOption.map(root => WatchSettings(new File(root))),
      nInstances = nInstances
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

