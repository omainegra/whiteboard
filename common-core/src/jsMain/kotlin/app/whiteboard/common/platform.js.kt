package app.whiteboard.common

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js

@JsModule("uuid")
@JsNonModule
internal external object uuid {
  fun v4(): String
}

public actual fun nextUUID(): String = uuid.v4()

internal actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Js) { config(this) }