package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.routes.configureCitiesRouting
import com.example.routes.configureUsersRouting
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(Resources)
        install(RequestValidation) {
            configureRequestValidation()
        }
        install(StatusPages) {
            configureStatusPages()
        }

        configureSerialization()
        configureDatabase()

        configureUsersRouting()
        configureCitiesRouting()
    }.start(wait = true)
}
