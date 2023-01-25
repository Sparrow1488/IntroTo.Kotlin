package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import com.example.services.authentication.AuthenticationManager
import com.example.services.authentication.IAuthenticationManager

fun Application.configureRouting() {
    val authManager: IAuthenticationManager = AuthenticationManager()

    routing {
        get("/") {
            if(authManager.isAuthenticated(call)){
                call.respondRedirect("/auth")
                return@get
            }
            call.respondText("Hello World!")
        }

        get("/auth") {
            authManager.authenticate(call)
            call.respondRedirect("/")
        }
    }
}
