package vvu.study.kotlin.demo.handlers

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

data class PongMessage(val message : String = "PONG")

@Component
class PingHandler {
    suspend fun pong(request: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyValueAndAwait(PongMessage())
}
