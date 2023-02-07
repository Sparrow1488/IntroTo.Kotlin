package com.site.infrastructure.exceptions

import io.ktor.http.*

class BadRequestException(message: String?) : ApiStatusException(message) {
    override val httpStatus = HttpStatusCode.BadRequest
}