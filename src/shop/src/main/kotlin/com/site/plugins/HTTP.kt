package com.site.plugins

import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import io.swagger.v3.oas.models.OpenAPI

fun Application.configureHTTP() {
//    routing {
//        swaggerUI(path = "swagger") {
//            version = "4.15.5"
//        }
//        openAPI(path="openapi") {
//            codegen = StaticHtmlCodegen()
//        }
//    }
}
