package com.site.plugins

import com.site.infrastructure.exceptions.ApiStatusException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ApiStatusException> { call, cause ->
            val errorResponse = cause.toSerializable()
            call.response.status(cause.httpStatus)
            call.respond(errorResponse)
        }
    }
}