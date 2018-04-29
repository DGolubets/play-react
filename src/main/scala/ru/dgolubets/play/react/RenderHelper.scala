package ru.dgolubets.play.react

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import io.circe.generic.AutoDerivation
import io.circe.syntax._
import io.circe.{Encoder, Json}
import play.api.http.MimeTypes
import play.api.mvc.Results._
import play.api.mvc.{RequestHeader, Result}
import play.twirl.api.Html

import ru.dgolubets.reactjs.server.RenderServer

private object RenderHelper extends AutoDerivation {

  def render(renderFunction: String, renderTemplate: RenderTemplate, status: Int)
            (implicit renderServer: RenderServer, request: RequestHeader): Future[Result] = {
    val state = RenderState[String](
      request.uri,
      status = status
    )
    renderState(renderFunction, renderTemplate, state.asJson, Status(status))
  }

  def render[T](renderFunction: String, renderTemplate: RenderTemplate, content: T, status: Int = 200)
               (implicit renderServer: RenderServer, request: RequestHeader, encoder: Encoder[T]): Future[Result] = {
    val state = RenderState(
      request.uri,
      status = status,
      content = Some(content)
    )
    renderState(renderFunction, renderTemplate, state.asJson, Status(status))
  }

  private def renderState[T](renderFunction: String, renderTemplate: RenderTemplate, state: Json, status: Status)
                            (implicit renderServer: RenderServer, request: RequestHeader): Future[Result] = {
    if (request.accepts(MimeTypes.HTML)) {
      renderServer.render(renderFunction, state).map { html =>
        status(renderTemplate(Html(html), state))
      }
    }
    else {
      val result = status(state.noSpaces).as(MimeTypes.JSON)
      Future.successful(result)
    }
  }
}
