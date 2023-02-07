package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object BasketProducts : IntIdTable("basket_products") {
    val productId = reference("product_id", Products, onDelete = ReferenceOption.CASCADE)
    val basketId = reference("basket_id", Baskets, onDelete = ReferenceOption.CASCADE)
}

class BasketProductDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BasketProductDAO>(BasketProducts)
    var product by ProductDAO referencedOn BasketProducts.productId
    var basket by BasketDAO referencedOn BasketProducts.basketId
}