package com.example.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    Database.connect("jdbc:postgresql://127.0.0.1:5432/postgres", driver = "org.postgresql.Driver",
        user = "postgres", password = "secret")

    transaction {
        SchemaUtils.create(Users, Cities)
    }
}
