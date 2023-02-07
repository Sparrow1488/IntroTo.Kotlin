package com.site.tables

import com.site.infrastructure.abstractions.IContractSerializable
import com.site.contracts.users.responses.UserResponse
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("users") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id), IContractSerializable<UserResponse> {
    companion object : IntEntityClass<UserDAO>(Users)
    var firstName by Users.firstName
    var lastName by Users.lastName
    val credentials by UserCredentialsDAO backReferencedOn UserCredentials.userId
    val basket by BasketDAO optionalBackReferencedOn Baskets.userId

    override fun toSerializable(): UserResponse = UserResponse(
        this.id.value,
        this.firstName,
        this.lastName
    )
}