package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Baskets : IntIdTable("baskets") {
    val user = reference("user_id", Users)
}

class BasketDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BasketDAO>(Baskets)
    val user by UserDAO referrersOn Baskets.user
}