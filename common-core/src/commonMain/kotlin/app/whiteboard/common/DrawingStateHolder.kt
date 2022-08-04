package app.whiteboard.common

import app.whiteboard.common.DrawingEvent.ColorChanged
import app.whiteboard.common.DrawingEvent.StrokeWidthChanged
import app.whiteboard.common.DrawingEvent.MotionEvent.Moved
import app.whiteboard.common.DrawingEvent.MotionEvent.Pressed
import app.whiteboard.common.DrawingEvent.MotionEvent.Released
import app.whiteboard.common.MotionState.Idle
import app.whiteboard.common.MotionState.Moving
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.milliseconds

public sealed class MotionState {
  public object Idle : MotionState()
  public object Moving : MotionState()
}

private val colors = listOf(
  Color(0, 0, 0),
  Color(128, 128, 128),
  Color(51, 153, 255),
  Color( 153, 51, 255),
  Color(255, 51, 255),
  Color(0, 255, 128),
  Color(255, 51, 51),
)

public data class DrawingState(
  val color: Color = colors.first(),
  val strokeWidth: Int = 1,
  val segments: List<Segment> = emptyList(),
  val motionState: MotionState = Idle,
  val colorOptions: List<Color> = colors,
)

public sealed class DrawingEvent {
  public sealed class MotionEvent : DrawingEvent() {
    public abstract val x: Float
    public abstract val y: Float

    public data class Pressed(override val x: Float, override val y: Float) : MotionEvent()
    public data class Moved(override val x: Float, override val y: Float) : MotionEvent()
    public data class Released(override val x: Float, override val y: Float) : MotionEvent()
  }

  public data class ColorChanged(val value: Color) : DrawingEvent()

  public data class StrokeWidthChanged(val value: Int) : DrawingEvent()
}

public interface DrawingStateHolder {
  public val state: StateFlow<DrawingState>

  public fun onEvent(event: DrawingEvent)

  public companion object {
    public operator fun invoke(): DrawingStateHolder = DrawingStateHolderImpl()
  }
}

internal class DrawingStateHolderImpl(
  private val api: Api = Api(),
  private val scope: CoroutineScope = MainScope()
) : DrawingStateHolder {

  private val _state = MutableStateFlow(DrawingState())

  override val state: StateFlow<DrawingState> = _state.asStateFlow()

  init {
    api.whiteBoardState()
      .onEach { boardState ->
        _state.update { it.copy(segments = boardState.segments) }
      }
      .launchIn(scope)

    state
      .mapNotNull { it.segments.lastOrNull() }
      .debounce(300.milliseconds)
      .distinctUntilChanged()
      .onEach { api.updateSegment(it) }
      .launchIn(scope)
  }

  override fun onEvent(event: DrawingEvent) {
    when (event) {
      is Moved -> onMove(event)
      is Pressed -> onPressed(event)
      is Released -> onReleased()
      is ColorChanged -> onChangeColor(event)
      is StrokeWidthChanged -> onChangeStrokeWidth(event)
    }
  }

  private fun onPressed(event: Pressed) {
    _state.update { current ->
      val newSegment = Segment(id =  nextUUID(), points = listOf(Point(event.x, event.y)), width = current.strokeWidth, color = current.color)
      current.copy(segments = current.segments + newSegment, motionState = Moving)
    }
  }

  private fun onMove(event: Moved) {
    if (state.value.motionState == Moving) {
      _state.update { current ->
        val segment = current.segments.last()
        val updated = segment.copy(points = segment.points + Point(event.x, event.y))

        current.copy(segments = current.segments.dropLast(1) + updated)
      }
    }
  }

  private fun onReleased() {
    _state.update { it.copy(motionState = Idle) }
  }

  private fun onChangeColor(event: ColorChanged) {
    _state.update { it.copy(color = event.value) }
  }

  private fun onChangeStrokeWidth(event: StrokeWidthChanged) {
    _state.update { it.copy(strokeWidth = event.value.coerceIn(0, 20)) }
  }
}



