package app.whiteboard.common

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

public expect fun nextUUID(): String

internal expect fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient
