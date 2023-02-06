package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("users") {
    val credentials = reference("credentials_id", UserCredentials)
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val basket = reference("basket_id", Baskets).nullable()
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(Users)
    val credentials by UserCredentialsDAO referrersOn Users.credentials
    var firstName by Users.firstName
    var lastName by Users.lastName
    val basket by BasketDAO optionalReferrersOn Users.basket
}