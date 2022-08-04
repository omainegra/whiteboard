package app.whiteboard.common

import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.ws
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.Application
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

public interface Api {
  public fun whiteBoardState(): Flow<WhiteBoardState>
  public suspend fun updateSegment(segment: Segment)

  public companion object {
    public operator fun invoke(): Api = ApiImpl()
  }
}

internal class ApiImpl(private val host: String = host(), private val port: Int = 8080) : Api {

  private val client = httpClient {
    install(ContentNegotiation) {
      json(jsonSerializer)
    }

    install(WebSockets) {
      contentConverter = KotlinxWebsocketSerializationConverter(jsonSerializer)
    }

    install(Logging) {
      logger = Logger.SIMPLE
      level = LogLevel.INFO
    }

    install(HttpTimeout) {
      requestTimeoutMillis = 10_000
    }
  }

  override fun whiteBoardState(): Flow<WhiteBoardState> = channelFlow {
    client.ws(host = host, port = port, path = "/whiteboard/state") {
      while (true) {
        send(receiveDeserialized<WhiteBoardState>())
      }
    }
  }

  override suspend fun updateSegment(segment: Segment) {
    client.post("http://$host:$port/whiteboard/segments") {
      contentType(Application.Json)
      setBody(segment)
    }
  }
}

private fun host(): String = TODO("Replace with ip address where server is running (e.g. 10.0.0.2)")