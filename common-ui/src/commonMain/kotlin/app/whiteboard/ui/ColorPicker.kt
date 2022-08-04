package app.whiteboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.whiteboard.common.Color as CommonColor

@Composable
fun ColorPicker(colorOptions: List<CommonColor>, modifier: Modifier = Modifier, onColorChanged: (Color) -> Unit) {
  val colors = colorOptions.map { it.toCompose() }

  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    for (color in colors) {
      Box(
        Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(color)
          .clickable { onColorChanged(color) }
      )
    }
  }
}