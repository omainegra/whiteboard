package app.whiteboard.common

import kotlinx.serialization.Serializable

@Serializable
public data class Color(val r: Int, val g: Int, val b: Int)

@Serializable
public data class Point(val x: Float, val y: Float)

@Serializable
public data class Segment(val id: String, val points: List<Point>, val width: Int, val color: Color)

@Serializable
public data class WhiteBoardState(
  public val width: Int,
  public val height: Int,
  public val segments: List<Segment>,
)
