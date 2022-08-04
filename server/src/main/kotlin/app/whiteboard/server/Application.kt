package app.whiteboard.server

import app.whiteboard.common.jsonSerializer
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets

fun main() {
  embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
  install(ContentNegotiation) {
    json(jsonSerializer)
  }

  install(CORS) {
    anyHost()
    allowMethod(HttpMethod.Put)
    allowHeader(HttpHeaders.ContentType)
    allowHeader(HttpHeaders.Authorization)
  }

  install(Compression)

  install(WebSockets) {
    contentConverter = KotlinxWebsocketSerializationConverter(jsonSerializer)
  }

  routing {
    whiteboard()
  }
}
