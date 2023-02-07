package com.site.contracts.users.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequest(
    var login: String,
    var password: String
)