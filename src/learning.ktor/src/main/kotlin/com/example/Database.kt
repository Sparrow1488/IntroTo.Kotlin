package com.example

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    Database.connect("jdbc:postgresql://127.0.0.1:5432/postgres", driver = "org.postgresql.Driver",
        user = "postgres", password = "secret")
}
