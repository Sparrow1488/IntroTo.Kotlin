package com.site.infrastructure.exceptions

import io.ktor.http.*

class ForbiddenException(
    message: String?,
    reason: String = "undefined_reason")
    : ApiStatusException(message)
{
    init {
        info["reason"] = reason
    }

    override val httpStatus = HttpStatusCode.Forbidden
}