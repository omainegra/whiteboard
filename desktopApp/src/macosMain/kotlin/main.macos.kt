import androidx.compose.ui.window.Window
import app.whiteboard.ui.WhiteBoard
import platform.AppKit.NSApp
import platform.AppKit.NSAppearance
import platform.AppKit.NSAppearanceNameAqua
import platform.AppKit.NSApplication
import platform.AppKit.NSApplicationDelegateProtocol
import platform.AppKit.NSWindow
import platform.AppKit.appearance
import platform.darwin.NSObject

fun main() {
  NSApplication.sharedApplication()

  Window {
    WhiteBoard()
  }

  NSApp?.apply {
    (windows.firstOrNull() as? NSWindow)?.title = "Whiteboard (Native Macos app)"
    appearance = NSAppearance.appearanceNamed(NSAppearanceNameAqua)

    delegate = object : NSObject(), NSApplicationDelegateProtocol {
      override fun applicationShouldTerminateAfterLastWindowClosed(sender: NSApplication): Boolean = true
    }

    run()
  }
}