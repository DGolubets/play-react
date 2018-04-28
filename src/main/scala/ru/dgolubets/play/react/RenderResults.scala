package ru.dgolubets.play.react

trait RenderResults {

  protected def renderFunction: RenderFunction

  def status(status: Int): RenderFunction = renderFunction.withStatus(status)

  def ok: RenderFunction = renderFunction.withStatus(200)

  def notFound: RenderFunction = renderFunction.withStatus(404)

  def badRequest: RenderFunction = renderFunction.withStatus(400)

  def internalServerError: RenderFunction = renderFunction.withStatus(500)
}
