package com.site.controllers

import com.site.infrastructure.constants.AppClaims
import com.site.contracts.users.requests.UserCreateRequest
import com.site.contracts.users.requests.UserLoginRequest
import com.site.infrastructure.services.identity.IdentityManager
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.configureAuthRouting() = route("/auth") {

    val identityManager = IdentityManager()

    post("/register") {
        val request = call.receive<UserCreateRequest>()
        val jwtToken = identityManager.RegisterUser(request)
        call.respond(hashMapOf("token" to jwtToken))
    }

    post("/login") {
        val request = call.receive<UserLoginRequest>()
        val jwtToken = identityManager.LoginUser(request)
        call.respond(hashMapOf("token" to jwtToken))
    }

    authenticate {
        get("/username") {
            val principal = call.principal<JWTPrincipal>()!!

            val id = principal[AppClaims.userId]
            val username = principal[AppClaims.username]
            call.respondText("$id: $username")
        }
    }
}