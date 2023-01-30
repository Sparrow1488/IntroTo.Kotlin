package com.example.plugins

import io.ktor.http.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val error: String = "unknown_error",
    val status: Int = 400)

fun StatusPagesConfig.configureStatusPages() {
    exception<RequestValidationException> { call, cause ->
        val errors = cause.reasons.joinToString("; ")
        call.respond(HttpStatusCode.BadRequest, ErrorResponse(errors, "validation_error"))
    }
}