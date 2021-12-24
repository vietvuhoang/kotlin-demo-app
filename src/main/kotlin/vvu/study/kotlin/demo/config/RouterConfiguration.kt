package vvu.study.kotlin.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter
import vvu.study.kotlin.demo.handlers.PingHandler

@Configuration
class RouterConfiguration {

    @Bean
    fun pingRoutes(pingHandler: PingHandler) = coRouter {
        GET("/ping", pingHandler::pong)
    }
}
