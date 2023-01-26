package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class ContentResponse(var text: String)

@Serializable
data class UserResponse(var name: String)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)
    val cityId = (integer("city_id") references Cities.id).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID")
}

object Cities : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Cities_ID")
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(ContentResponse(text = "Hello World!"))
        }

        get("/users") {
            val users = transaction {
                Users.selectAll()
                     .map { UserResponse(it[Users.name]) }
                     .toList()
            }
            call.respond(users)
        }
    }
}
