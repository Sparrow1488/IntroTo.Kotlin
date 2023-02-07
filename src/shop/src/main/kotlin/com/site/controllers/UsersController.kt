package com.site.controllers

import com.site.constants.AppClaims
import com.site.tables.BasketDAO
import com.site.tables.BasketProductDAO
import com.site.tables.ProductDAO
import com.site.tables.UserDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureUsersRouting() = routing {
    authenticate {
        get("/users/me/basket/products") {
            val customer = getCustomer()

            val basketProducts = transaction {
                customer.basket?.products?.map { it.product.toSerializable() }
            }
            call.respond(basketProducts ?: listOf())
        }

        post("/users/me/basket/products/{productId}") {
            val customer = getCustomer()

            val productId = call.parameters["productId"]!!.toInt()
            val existsProduct = transaction {
                ProductDAO.findById(productId)
            } ?: throw Exception("Product not found")

            val userBasket = transaction {
                if (customer.basket == null) {
                    BasketDAO.new { user = customer }
                }
                customer.basket
            }!!

            val basketProducts = transaction {
                BasketProductDAO.new {
                    product = existsProduct
                    basket = userBasket
                }.basket.products.map { it.product.toSerializable() }
            }
            call.respond(basketProducts)
        }

        delete("/users/me/basket") {
            val customer = getCustomer()

            transaction {
                customer.basket?.delete()
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.getCustomer() : UserDAO {
    val userIdentity = call.principal<JWTPrincipal>()!!
    val userId = userIdentity.payload.getClaim(AppClaims.userId).asInt()
    return transaction {
        UserDAO.findById(userId)
    } ?: throw Exception("User not found")
}