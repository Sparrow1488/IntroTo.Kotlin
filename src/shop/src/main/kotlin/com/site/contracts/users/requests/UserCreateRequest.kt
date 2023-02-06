package com.site.contracts.users.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserCreateRequest(
    var login: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var phone: String?,
    var email: String?
)