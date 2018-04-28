package ru.dgolubets.play.react

import io.circe.Json
import play.twirl.api.Html

trait RenderTemplate {
  def apply(markup: Html, state: Json): Html
}

object RenderTemplate {

  object IdentityTemplate extends RenderTemplate {
    override def apply(markup: Html, state: Json): Html = markup
  }

}