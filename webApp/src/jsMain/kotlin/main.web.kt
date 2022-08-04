package app.whiteboard.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.whiteboard.common.Color
import app.whiteboard.common.DrawingEvent
import app.whiteboard.common.DrawingEvent.MotionEvent
import app.whiteboard.common.DrawingStateHolder
import app.whiteboard.common.Segment
import org.jetbrains.compose.web.attributes.height
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.attributes.width
import org.jetbrains.compose.web.css.DisplayStyle.Companion.Flex
import org.jetbrains.compose.web.css.DisplayStyle.Companion.InlineBlock
import org.jetbrains.compose.web.css.FlexDirection.Companion.Column
import org.jetbrains.compose.web.css.FlexDirection.Companion.Row
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.gap
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.textDecoration
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Canvas
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.Select
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposableInBody
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import org.jetbrains.compose.web.css.Color as CSSColor


fun main() {
  renderComposableInBody {
    WhiteBoard()
  }
}

@Composable
fun WhiteBoard(stateHolder: DrawingStateHolder = DrawingStateHolder()) {
  val state by stateHolder.state.collectAsState()

  Div(attrs = {
    style {
      display(Flex)
      flexDirection(Column)
    }
  }) {
    H1 { Text("Whiteboard (Compose Web)") }

    WhiteBoardCanvas(
      segments = state.segments,
      onMouseDown = { (x, y) -> stateHolder.onEvent(MotionEvent.Pressed(x, y)) },
      onMouseMove = { (x, y) -> stateHolder.onEvent(MotionEvent.Moved(x, y)) },
      onMouseUp = { (x, y) -> stateHolder.onEvent(MotionEvent.Released(x, y)) },
    )

    ColorPicker(
      colorOptions = state.colorOptions,
      onChangeColor = { stateHolder.onEvent(DrawingEvent.ColorChanged(it)) },
      onChangeStrokeWidth = { stateHolder.onEvent(DrawingEvent.StrokeWidthChanged(it)) }
    )
  }
}

@Composable
fun WhiteBoardCanvas(
  segments: List<Segment>,
  onMouseDown: (Pair<Float, Float>) -> Unit,
  onMouseMove: (Pair<Float, Float>) -> Unit,
  onMouseUp: (Pair<Float, Float>) -> Unit,
) {
  Canvas(attrs = {
    width(720)
    height(1080)

    style {
      width(600.px)
      height(800.px)
    }
  }) {
    DisposableEffect(segments) {
      fun getXY(event: Event): Pair<Float, Float> {
        check(event is MouseEvent)

        val rect = scopeElement.getBoundingClientRect()
        val scaleX = scopeElement.width / rect.width
        val scaleY = scopeElement.height / rect.height

        return ((event.clientX - rect.left)*scaleX).toFloat() to ((event.clientY - rect.top)*scaleY).toFloat()
      }

      val mouseDownListener = { event: Event ->
        onMouseDown(getXY(event))
      }

      val mouseMoveListener = { event: Event ->
        onMouseMove(getXY(event))
      }

      val mouseUpListener = { event: Event ->
        onMouseUp(getXY(event))
      }

      scopeElement.addEventListener(type = "mousemove", callback = mouseMoveListener)
      scopeElement.addEventListener(type = "mousedown", callback = mouseDownListener)
      scopeElement.addEventListener(type = "mouseup", callback = mouseUpListener)

      onDispose {
        scopeElement.removeEventListener(type = "mousemove", callback = mouseMoveListener)
        scopeElement.removeEventListener(type = "mousedown", callback = mouseDownListener)
        scopeElement.removeEventListener(type = "mouseup", callback = mouseUpListener)
      }
    }

    DisposableEffect(segments) {
      val context2D = scopeElement.getContext("2d") as CanvasRenderingContext2D

      with(context2D) {
        clearRect(0.0, 0.0, scopeElement.width.toDouble(), scopeElement.height.toDouble())

        segments.forEach { segment ->
          if (segment.points.size >= 2) {
            val first = segment.points.first()
            val tail = segment.points.drop(1)

            strokeStyle = segment.color.toWeb()
            lineWidth = segment.width.toDouble()

            beginPath()
            moveTo(first.x.toDouble(), first.y.toDouble())

            tail.forEach { point ->
              lineTo(point.x.toDouble(), point.y.toDouble())
            }

            stroke()
          }
        }
      }

      onDispose {  }
    }
  }
}

@Composable
fun ColorPicker(
  colorOptions: List<Color>,
  onChangeColor: (Color) -> Unit,
  onChangeStrokeWidth: (Int) -> Unit
) {
  var selectedWidth by remember { mutableStateOf(1) }

  LaunchedEffect(selectedWidth) {
    onChangeStrokeWidth(selectedWidth)
  }

  Div(attrs = {
    style {
      display(Flex)
      flexDirection(Row)
      gap(8.px)
    }
  }) {
    colorOptions.forEach { color ->
      RoundButton(color = color, onClick = { onChangeColor(color) })
    }

    Select(attrs = {
      onChange { selectedWidth = it.value!!.toInt() }
    }) {
      (1..20).forEach { width ->
        Option(
          attrs = {
            if (selectedWidth == width) {
              selected()
            }

            style {
              color(CSSColor.white)
            }
          },
          value = width.toString()
        ) {
          Text(width.toString())
        }
      }
    }
  }
}

@Composable
fun RoundButton(color: Color, onClick: () -> Unit) {
  Button(attrs = {
    style {
      width(32.px)
      height(32.px)
      property("border", "none")
      borderRadius(50.percent)
      color(color.toWeb())
      backgroundColor(color.toWeb())
      textDecoration("none")
      display(InlineBlock)
    }
    onClick { onClick()  }
  })
}

private fun Color.toWeb() = rgb(r, g, b)