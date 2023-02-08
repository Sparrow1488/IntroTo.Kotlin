package com.site.infrastructure.exceptions

import com.site.contracts.errors.responses.ErrorResponse

abstract class ApiStatusException(
    message: String?
) : Exception(message), IStatusCodeException {

    val info: HashMap<String, String> = hashMapOf()

    fun toSerializable(): ErrorResponse = ErrorResponse(
        message ?: "Empty",
        httpStatus.value,
        info
    )
}