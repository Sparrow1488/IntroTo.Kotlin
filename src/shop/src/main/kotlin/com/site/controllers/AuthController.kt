package com.site.controllers

import com.site.constants.AppClaims
import com.site.contracts.users.requests.UserCreateRequest
import com.site.contracts.users.requests.UserLoginRequest
import com.site.services.IdentityManager
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAuthRouting() = routing {
    val identityManager = IdentityManager()
    post("/auth/register") {
        val request = call.receive<UserCreateRequest>()
        val jwtToken = identityManager.RegisterUser(request)
        call.respond(hashMapOf("token" to jwtToken))
    }

    post("/auth/login") {
        val request = call.receive<UserLoginRequest>()
        val jwtToken = identityManager.LoginUser(request)
        call.respond(hashMapOf("token" to jwtToken))
    }

    authenticate {
        get("/auth/username") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!![AppClaims.username]
            call.respondText(username ?: "null")
        }
    }
}