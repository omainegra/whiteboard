package app.whiteboard.ui

import androidx.compose.ui.graphics.Color
import app.whiteboard.common.Color as CommonColor

fun CommonColor.toCompose(): Color =  Color(r, g, b)

fun Color.toCommon(): CommonColor {
  fun component(value: Float)  = (value*255f).toInt()
  return CommonColor(component(red), component(green), component(blue))
}