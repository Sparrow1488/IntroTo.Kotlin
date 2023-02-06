package com.site.services

import com.site.contracts.users.requests.UserCreateRequest
import com.site.contracts.users.requests.UserLoginRequest

interface IIdentityManager {
    fun RegisterUser(request: UserCreateRequest) : String
    fun LoginUser(request: UserLoginRequest) : String
}