package app.whiteboard.server

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun Route.whiteboard() {

  val stateHolder = WhiteBoardStateHolder()

  route("/whiteboard") {
    webSocket("/state") {
      coroutineScope {
        stateHolder.state
          .onEach(::sendSerialized)
          .launchIn(this)
      }
    }

    post("/segments") {
      stateHolder.update(call.receive())
      call.respond(HttpStatusCode.NoContent)
    }
  }
}