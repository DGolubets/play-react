package ru.dgolubets.play.react

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import io.circe.Encoder
import play.api.mvc.{RequestHeader, Result}

import ru.dgolubets.reactjs.server.RenderServer

case class RenderFunction(functionName: String,
                          template: RenderTemplate = RenderTemplate.IdentityTemplate,
                          status: Int = 200,
                          headers: Map[String, String] = Map.empty
                         ) extends RenderResults {

  override protected def renderFunction: RenderFunction = this

  def withTemplate(template: RenderTemplate): RenderFunction = this.copy(template = template)

  def withHeaders(headers: Map[String, String]): RenderFunction = this.copy(headers = headers)

  def withStatus(status: Int): RenderFunction = this.copy(status = status)

  def apply()(implicit request: RequestHeader, renderServer: RenderServer): Future[Result] = {
    RenderHelper.render(functionName, template, status).map(_.withHeaders(headers.toSeq: _*))
  }

  def apply[T](content: T)(implicit request: RequestHeader, encoder: Encoder[T], renderServer: RenderServer): Future[Result] = {
    RenderHelper.render(functionName, template, content, status).map(_.withHeaders(headers.toSeq: _*))
  }
}

