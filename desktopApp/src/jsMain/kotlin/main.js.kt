import androidx.compose.ui.window.Window
import app.whiteboard.ui.WhiteBoard
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
  onWasmReady {
    Window("WhiteBoard") {
      WhiteBoard()
    }
  }
}