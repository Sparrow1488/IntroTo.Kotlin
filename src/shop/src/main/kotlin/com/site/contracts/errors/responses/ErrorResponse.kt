package com.site.contracts.errors.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val status: Int,
    val info: Map<String, String>
)