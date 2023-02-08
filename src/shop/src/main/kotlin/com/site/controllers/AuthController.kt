package com.site.controllers

import com.site.contracts.tokens.responses.JwtTokenResponse
import com.site.infrastructure.constants.PrincipalDefaults
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
        val jwtToken = identityManager.registerUser(request)
        call.respond(JwtTokenResponse(jwtToken))
    }

    post("/login") {
        val request = call.receive<UserLoginRequest>()
        val jwtToken = identityManager.loginUser(request)
        call.respond(JwtTokenResponse(jwtToken))
    }

    authenticate {
        get("/username") {
            val principal = call.principal<JWTPrincipal>()!!

            val id = principal[PrincipalDefaults.ClaimNames.USER_ID]
            val username = principal[PrincipalDefaults.ClaimNames.USERNAME]
            call.respondText("$id: $username")
        }
    }
}