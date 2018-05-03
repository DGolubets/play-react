package ru.dgolubets.play.react

import scala.concurrent.Future

import akka.actor.ActorSystem
import io.circe.Json

import ru.dgolubets.reactjs.server.{RenderServer, RenderServerSettings}

class PlayRenderServer(settings: RenderServerSettings,
                       mock: Option[String] = None)(implicit system: ActorSystem) extends RenderServer(settings) {
  override def render(functionName: String, state: Json): Future[String] = {
    mock match {
      case None =>
        super.render(functionName, state)
      case Some(html) =>
        Future.successful(html)
    }
  }
}
