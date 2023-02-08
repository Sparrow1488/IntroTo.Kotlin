package com.site.controllers

import com.site.infrastructure.exceptions.NotFoundException
import com.site.infrastructure.services.users.UserSession
import com.site.tables.BasketDAO
import com.site.tables.BasketProductDAO
import com.site.tables.ProductDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.configureUsersRouting() = route("/users") {
    val session = UserSession()

    authenticate {
        route("me") {
            get("/basket/products") {
                val customer = session.getUser(call)

                val basketProducts = transaction {
                    customer.basket?.products?.map { it.product.toSerializable() }
                }
                call.respond(basketProducts ?: listOf())
            }

            post("/basket/products/{productId}") {
                val customer = session.getUser(call)

                val productId = call.parameters["productId"]!!.toInt()
                val existsProduct = transaction {
                    ProductDAO.findById(productId)
                } ?: throw NotFoundException("Product not found", productId.toString(), "Product")

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

            delete("/basket") {
                val customer = session.getUser(call)

                transaction {
                    customer.basket?.delete()
                }
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}