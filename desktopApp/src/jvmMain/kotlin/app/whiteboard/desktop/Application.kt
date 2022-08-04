package app.whiteboard.desktop

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.whiteboard.ui.WhiteBoard

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "Whiteboard (JVM Application)",
    state = rememberWindowState(width = 480.dp, height = 720.dp),
    content = { WhiteBoard() }
  )
}
