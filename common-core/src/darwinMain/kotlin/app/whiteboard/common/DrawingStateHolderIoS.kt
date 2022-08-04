package app.whiteboard.common

public class DrawingStateHolderIoS(private val impl: DrawingStateHolder) : DrawingStateHolder by impl {

  public constructor(): this(DrawingStateHolderImpl())

  public companion object {
    public val InitialDrawingState: DrawingState = DrawingState()
  }
}

