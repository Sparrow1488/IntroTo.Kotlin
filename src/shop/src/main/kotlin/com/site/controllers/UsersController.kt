package com.site.controllers

import com.site.constants.AppClaims
import com.site.tables.BasketDAO
import com.site.tables.BasketProductDAO
import com.site.tables.ProductDAO
import com.site.tables.UserDAO
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureUsersRouting() = routing {
//    authenticate {
//        post("/users/me/basket/products/{productId}") {
//            val userIdentity = call.principal<JWTPrincipal>()!!
//            val userId = userIdentity.payload.getClaim(AppClaims.userId).asInt()
//            val existsUser = transaction {
//                UserDAO.findById(userId)
//            } ?: throw Exception("User not found")
//
//            val productId = call.parameters["productId"]!!.toInt()
//            val existsProduct = transaction {
//                ProductDAO.findById(productId)
//            } ?: throw Exception("User not found")
//
//            val basket = transaction {
//                if (existsUser.basket == null) {
//                    BasketDAO.new {
//                        user = existsUser
//                    }
//                }
//                existsUser.basket
//            }
//
//            val basketProducts = transaction {
//                basket!!.products.plus(
//                    BasketProductDAO.new {
//                        product = existsProduct
//                    }
//                ).map { it.product.toSerializable() }
//            }
//            call.respond(basketProducts)
//        }
//    }

}