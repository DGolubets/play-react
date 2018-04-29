package ru.dgolubets.play.react

private case class RenderState[T](uri: String,
                                  status: Int,
                                  content: Option[T] = None)
