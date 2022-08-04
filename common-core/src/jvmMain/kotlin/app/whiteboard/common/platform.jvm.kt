package app.whiteboard.common

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import java.util.UUID

public actual fun nextUUID(): String = UUID.randomUUID().toString()

internal actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(OkHttp) { config(this) }

