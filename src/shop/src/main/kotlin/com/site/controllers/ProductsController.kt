package com.site.controllers

import com.site.contracts.products.requests.ProductCreateRequest
import com.site.infrastructure.exceptions.NotFoundException
import com.site.tables.FileDAO
import com.site.tables.ProductDAO
import com.site.tables.ProductFileDAO
import com.site.tables.ShopDAO
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureProductsRouting() = routing {
    get("/products/latest") {
        val products = transaction {
            ProductDAO.all().map { it.toSerializable() }
        }
        call.respond(products)
    }

    get("/products/{id}") {
        val productId = call.parameters["id"]!!.toInt()
        val existsProduct = transaction {
            ProductDAO.findById(productId)?.toSerializable()
                ?: throw NotFoundException("Product not found", productId.toString(), "Product")
        }
        call.respond(existsProduct)
    }

    post("/products/new") {
        val request = call.receive<ProductCreateRequest>()
        val existsShop = transaction {
            ShopDAO.findById(request.shopId)
        } ?: throw NotFoundException("Shop not found", request.shopId.toString(), "Shop")

        val createdProduct = transaction {
            ProductDAO.new {
                name = request.name
                description = request.description
                price = request.price
                shop = existsShop
            }.toSerializable()
        }
        call.respond(createdProduct)
    }

    post("/products/{productId}/files/{fileId}") {
        val productId = call.parameters["productId"]!!.toInt()
        val fileId = call.parameters["fileId"]!!.toInt()

        val existsProduct = transaction {
            ProductDAO.findById(productId)
        } ?: throw NotFoundException("Product not found", productId.toString(), "Product")

        val existsFile = transaction {
            FileDAO.findById(fileId)
        } ?: throw NotFoundException("File not found", fileId.toString(), "File")

        val updatedProduct = transaction {
            ProductFileDAO.new {
                product = existsProduct
                file = existsFile
            }.product.toSerializable()
        }
        
        call.respond(updatedProduct)
    }
}
