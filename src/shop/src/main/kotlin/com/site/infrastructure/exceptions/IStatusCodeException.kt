package com.site.infrastructure.exceptions

import io.ktor.http.*

interface IStatusCodeException {
    val httpStatus: HttpStatusCode
}