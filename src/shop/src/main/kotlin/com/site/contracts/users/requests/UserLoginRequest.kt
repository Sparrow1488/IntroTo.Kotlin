package com.site.contracts.users.requests

data class UserLoginRequest(
    var login: String,
    var password: String
)