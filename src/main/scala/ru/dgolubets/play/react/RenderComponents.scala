package ru.dgolubets.play.react

import akka.actor.ActorSystem
import play.api.BuiltInComponentsFromContext
import play.api.http.HttpErrorHandler

import ru.dgolubets.reactjs.server.RenderServer

trait RenderComponents {
  self: BuiltInComponentsFromContext =>

  private implicit def system: ActorSystem = actorSystem

  /**
    * Render configuration loaded from Typesafe config file
    */
  implicit lazy val renderConfiguration: RenderConfiguration = RenderConfiguration.load()

  /**
    * Render server
    */
  implicit lazy val renderServer: RenderServer = {
    val mockHtml = if(renderConfiguration.enabled) None else Some("")
    new PlayRenderServer(renderConfiguration.serverSettings, mockHtml)
  }

  /**
    * Default render function
    */
  implicit lazy val renderFunction: RenderFunction = renderConfiguration.defaultRenderFunction

  /**
    * Error handler integrated with rendering
    */
  override lazy val httpErrorHandler: HttpErrorHandler = new RenderErrorHandler(self.environment)
}
