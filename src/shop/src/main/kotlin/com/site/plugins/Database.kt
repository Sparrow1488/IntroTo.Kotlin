package com.site.plugins

import com.site.tables.Products
import com.site.tables.Shops
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() = routing {
    Database.connect(
        "jdbc:postgresql://127.0.0.1:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "secret")

    transaction {
        SchemaUtils.create(Products, Shops)
    }
}