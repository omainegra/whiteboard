package app.whiteboard.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StrokeWidthPicker(modifier: Modifier = Modifier, value: Int, onValueChanged: (Int) -> Unit) {
  StrokeWidthPickerStepper(
    modifier = modifier,
    value = value,
    onIncrement = { onValueChanged(value + 1) },
    onDecrement = { onValueChanged(value - 1) }
  )
}

@Composable
internal fun StrokeWidthPickerStepper(modifier: Modifier = Modifier, value: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    TextButton(onClick = onDecrement) {
      Text("-")
    }

    Text(value.toString())

    TextButton(onClick = onIncrement) {
      Text("+")
    }
  }
}