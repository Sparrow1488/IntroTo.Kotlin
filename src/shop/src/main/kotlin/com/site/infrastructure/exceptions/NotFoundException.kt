package com.site.infrastructure.exceptions

import io.ktor.http.*

class NotFoundException(
    message: String?,
    entityId: String = "unknown_entity_id",
    entityType: String = "unknown_entity_type"
) : ApiStatusException(message) {

    init {
        info["entity_id"] = entityId
        info["entity_type"] = entityType
    }

    override val httpStatus = HttpStatusCode.NotFound
}