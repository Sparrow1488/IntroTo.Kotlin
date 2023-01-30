package com.example.plugins

import io.ktor.server.auth.*

fun AuthenticationConfig.configureAuthentication() {
    basic("basic") {
        realm = "Access to '/' path"
        validate { credentials ->
            if(credentials.name == "user" && credentials.password == "1234")
                UserIdPrincipal(credentials.name)
            else null
        }
    }
}