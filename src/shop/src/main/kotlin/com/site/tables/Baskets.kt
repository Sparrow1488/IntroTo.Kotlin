package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Baskets : IntIdTable("baskets") {
    val userId = reference("user_id", Users, onDelete = ReferenceOption.CASCADE)
}

class BasketDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BasketDAO>(Baskets)
    var user by UserDAO referencedOn Baskets.userId
    val products by BasketProductDAO referrersOn BasketProducts.basketId
}