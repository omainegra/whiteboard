package app.whiteboard.common

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSUUID

public actual fun nextUUID(): String = NSUUID().UUIDString()

internal actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient =
  HttpClient(Darwin) {
    config(this)

    engine {
      configureRequest {
        setAllowsCellularAccess(true)
      }
    }
  }