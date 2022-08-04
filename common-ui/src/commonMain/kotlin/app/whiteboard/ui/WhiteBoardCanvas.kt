package app.whiteboard.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import app.whiteboard.common.DrawingEvent.MotionEvent
import app.whiteboard.common.DrawingEvent.MotionEvent.Moved
import app.whiteboard.common.DrawingEvent.MotionEvent.Pressed
import app.whiteboard.common.DrawingEvent.MotionEvent.Released
import app.whiteboard.common.DrawingState

@Composable
fun WhiteBoardCanvas(modifier: Modifier = Modifier, state: DrawingState, onEvent: (MotionEvent) -> Unit) {
  Box(
    modifier
      .pointerInput(Unit) {
        awaitPointerEventScope {
          while (true) {
            val event = awaitPointerEvent()
            val position = event.changes.first().position

            val motionEvent = when (event.type) {
              PointerEventType.Press -> Pressed(position.x, position.y)
              PointerEventType.Move -> Moved(position.x, position.y)
              PointerEventType.Release -> Released(position.x, position.y)
              else -> null
            }

            motionEvent?.let(onEvent)
          }
        }
      },
  ) {
    Canvas(Modifier.fillMaxSize()) {
      for (segment in state.segments) {
        drawPoints(
          points = segment.points.map { Offset(it.x, it.y) },
          pointMode = PointMode.Polygon,
          color = segment.color.toCompose(),
          strokeWidth = segment.width.toFloat(),
          pathEffect = PathEffect.cornerPathEffect(50f)
        )
      }
    }
  }
}