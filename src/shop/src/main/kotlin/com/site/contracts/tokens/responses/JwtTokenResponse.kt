package com.site.contracts.tokens.responses

import kotlinx.serialization.Serializable

@Serializable
data class JwtTokenResponse(
    val jwtToken: String
    // expires at or lifetime
)