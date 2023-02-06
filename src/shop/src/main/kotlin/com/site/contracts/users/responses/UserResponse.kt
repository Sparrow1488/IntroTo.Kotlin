package com.site.contracts.users.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    var id: Int,
    var firstName: String,
    var lastName: String
)