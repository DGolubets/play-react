package ru.dgolubets.play.react

private case class RenderState[T](path: String,
                                  status: Int,
                                  content: Option[T] = None)
