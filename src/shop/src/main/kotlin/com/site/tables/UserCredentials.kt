package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object UserCredentials : IntIdTable("user_credentials") {
    val userId = reference("user_id", Users, onDelete = ReferenceOption.CASCADE)
    val login = varchar("login", 30)
    val hashedPassword = varchar("hashed_password", 1000)
    val email = varchar("email", 50).nullable()
    val phone = varchar("phone", 50).nullable()
}

class UserCredentialsDAO(
    id: EntityID<Int>
) : IntEntity(id) {

    companion object : IntEntityClass<UserCredentialsDAO>(UserCredentials)

    var user by UserDAO referencedOn UserCredentials.userId
    var login by UserCredentials.login
    var hashedPassword by UserCredentials.hashedPassword
    var email by UserCredentials.email
    var phone by UserCredentials.phone
}