package com.example.services.authentication

import io.ktor.http.*
import io.ktor.server.application.*
import java.util.*

class AuthenticationManager : IAuthenticationManager {
    private val userIdCookieName = "ktor.auth.user_id"

    override suspend fun isAuthenticated(call: ApplicationCall): Boolean {
        val userId = call.request.cookies[userIdCookieName]
        return !userId.isNullOrEmpty()
    }

    override suspend fun authenticate(call: ApplicationCall) {
        val uuid = UUID.randomUUID()
        call.response.cookies.append(Cookie(userIdCookieName, uuid.toString()))
    }
}