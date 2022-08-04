package app.whiteboard.server

import app.whiteboard.common.Segment
import app.whiteboard.common.WhiteBoardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WhiteBoardStateHolder {

  private val _state = MutableStateFlow(
      WhiteBoardState(
          width = 300,
          height = 300,
          segments = emptyList(),
      )
  )

  val state: StateFlow<WhiteBoardState> = _state.asStateFlow()

  fun update(segment: Segment) {
    _state.update { current ->
      val existing = current.segments.find { it.id == segment.id }

      if (existing == null) {
        current.copy(segments = current.segments + segment)
      } else {
        val segments = current.segments - existing + segment
        current.copy(segments = segments)
      }
    }
  }
}