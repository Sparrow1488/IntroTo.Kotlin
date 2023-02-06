package com.site.controllers

import com.site.contracts.shops.requests.ShopCreateRequest
import com.site.tables.ShopDAO
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureShopsRouting() = routing {
    get("/shops/all") {
        val shops = transaction {
            ShopDAO.all().map { it.toSerializable() }
        }
        call.respond(shops)
    }

    post("/shops/new") {
        val request = call.receive<ShopCreateRequest>()
        val created = transaction {
            ShopDAO.new {
                title = request.title
            }.toSerializable()
        }
        call.respond(created)
    }
}