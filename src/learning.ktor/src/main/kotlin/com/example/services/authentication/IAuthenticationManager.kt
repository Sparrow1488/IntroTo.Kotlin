package com.example.services.authentication

import io.ktor.server.application.*

interface IAuthenticationManager {
    suspend fun isAuthenticated(call: ApplicationCall) : Boolean
    suspend fun authenticate(call: ApplicationCall)
}