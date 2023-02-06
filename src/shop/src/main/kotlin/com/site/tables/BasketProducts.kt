package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object BasketProducts : IntIdTable("basket_products") {
    val product = reference("product_id", Products)
    val basket = reference("basket_id", Baskets)
}

class BasketProductDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BasketProductDAO>(BasketProducts)
    val product by BasketProductDAO referrersOn BasketProducts.product
    val basket by BasketDAO referrersOn BasketProducts.basket
}