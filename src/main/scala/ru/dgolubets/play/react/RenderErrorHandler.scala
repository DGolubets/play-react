package ru.dgolubets.play.react

import scala.concurrent.Future

import com.typesafe.scalalogging.LazyLogging
import play.api.http.HttpErrorHandler
import play.api.mvc.{RequestHeader, Result}
import play.api.{Environment, Mode}

import ru.dgolubets.reactjs.server.RenderServer

class RenderErrorHandler(environment: Environment)(implicit renderServer: RenderServer, render: RenderFunction)
  extends HttpErrorHandler with LazyLogging {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    implicit val req = request
    render.withStatus(statusCode)(message)
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logger.error("Server Error", exception)
    implicit val req = request
    val errorText = environment.mode match {
      case Mode.Prod => "Unexpected server error."
      case _ => exception.toString
    }
    render.withStatus(500)(errorText)
  }
}
