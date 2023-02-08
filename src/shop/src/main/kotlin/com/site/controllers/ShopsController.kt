package com.site.controllers

import com.site.contracts.shops.requests.ShopCreateRequest
import com.site.infrastructure.exceptions.BadRequestException
import com.site.infrastructure.exceptions.NotFoundException
import com.site.infrastructure.services.users.UserSession
import com.site.tables.ShopDAO
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.configureShopsRouting() = route("/shops") {
    val session = UserSession()

    get("/all") {
        val shops = transaction {
            ShopDAO.all().map { it.toSerializable() }
        }
        call.respond(shops)
    }

    get("/{id}") {
        val shopId = call.parameters["id"]!!.toInt()
        val existsShop = transaction {
            ShopDAO.findById(shopId)?.toSerializable()
                ?: throw NotFoundException("Shop not found", shopId.toString(), "Shop")
        }
        call.respond(existsShop)
    }

    authenticate {
        post("/new") {
            val user = session.getUser(call)

            val userShopsTitles = transaction {
                user.shops.map {
                    it.title
                }
            }
            if(userShopsTitles.any()) {
                throw BadRequestException("User already has shop named ${userShopsTitles.first()}")
            }

            val request = call.receive<ShopCreateRequest>()
            val created = transaction {
                ShopDAO.new {
                    title = request.title
                    owner = user
                }.toSerializable()
            }
            call.respond(created)
        }
    }
}