package com.site.infrastructure.services.identity

import com.site.contracts.users.requests.UserCreateRequest
import com.site.contracts.users.requests.UserLoginRequest

interface IIdentityManager {
    fun registerUser(request: UserCreateRequest) : String
    fun loginUser(request: UserLoginRequest) : String
}