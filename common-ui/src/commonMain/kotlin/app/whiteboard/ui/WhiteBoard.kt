package app.whiteboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.whiteboard.common.DrawingEvent.ColorChanged
import app.whiteboard.common.DrawingEvent.StrokeWidthChanged
import app.whiteboard.common.DrawingStateHolder

@Composable
fun WhiteBoard(stateHolder: DrawingStateHolder = DrawingStateHolder()) {
  val state by stateHolder.state.collectAsState()

    MaterialTheme {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        WhiteBoardCanvas(modifier = Modifier.weight(1f), state = state, stateHolder::onEvent)

        Row(
          modifier = Modifier.fillMaxWidth().padding(8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          ColorPicker(
            modifier = Modifier.weight(1f).wrapContentHeight(),
            colorOptions = state.colorOptions,
            onColorChanged = { stateHolder.onEvent(ColorChanged(it.toCommon())) },
          )

          StrokeWidthPicker(modifier = Modifier.wrapContentSize(), value = state.strokeWidth, onValueChanged = { stateHolder.onEvent(StrokeWidthChanged(it)) })
        }
      }
    }
}