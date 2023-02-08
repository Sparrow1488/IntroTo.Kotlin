package com.site.plugins

import com.site.controllers.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import java.io.File

fun Application.configureRouting() = routing {
    configureProductsRouting()
    configureShopsRouting()
    configureAuthRouting()
    configureUsersRouting()
    configureFilesRouting()
}

