package com.example.plugins

import com.example.contracts.UserCreateRequest
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.configureRequestValidation() {
    validate<UserCreateRequest> { request ->
        if(request.name.isEmpty())
            ValidationResult.Invalid("Name should not be null or empty")
        else ValidationResult.Valid
    }
}