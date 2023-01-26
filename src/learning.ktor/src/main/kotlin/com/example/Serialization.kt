package com.example

import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    // Согласование сервера с клиентом формата для общения (ContentNegotiation)
    install(ContentNegotiation) {
        json()
    }
}