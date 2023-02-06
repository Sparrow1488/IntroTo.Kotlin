package com.site.tables

import com.site.abstractions.IContractSerializable
import com.site.contracts.users.responses.UserResponse
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

class UserDAO(id: EntityID<Int>) : IntEntity(id), IContractSerializable<UserResponse> {
    companion object : IntEntityClass<UserDAO>(Users)
    var credentials by UserCredentialsDAO referencedOn Users.credentials
    var firstName by Users.firstName
    var lastName by Users.lastName
    var basket by BasketDAO optionalReferencedOn Users.basket
    override fun toSerializable(): UserResponse = UserResponse(
        this.id.value,
        this.firstName,
        this.lastName
    )
}