package com.site.plugins

import com.site.controllers.configureAuthRouting
import com.site.controllers.configureProductsRouting
import com.site.controllers.configureShopsRouting
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
fun Application.configureRouting() {
    configureProductsRouting()
    configureShopsRouting()
    configureAuthRouting()

    routing {
        static("/static") {
            resources("static")
        }
    }
}
