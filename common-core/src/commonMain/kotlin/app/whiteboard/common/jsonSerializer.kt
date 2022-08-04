package app.whiteboard.common

import kotlinx.serialization.json.Json

public val jsonSerializer: Json = Json {
  prettyPrint = true
  isLenient = true
  ignoreUnknownKeys = true
}