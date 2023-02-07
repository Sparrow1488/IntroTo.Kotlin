package com.site.plugins

import com.site.controllers.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    configureProductsRouting()
    configureShopsRouting()
    configureAuthRouting()
    configureUsersRouting()
    configureFilesRouting()

    routing {
        static("/static") {
            resources("static")
        }
    }
}
