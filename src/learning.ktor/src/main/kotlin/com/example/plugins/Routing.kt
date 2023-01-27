package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.resources.get
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class ContentResponse(var text: String)

@Serializable
data class UserResponse(var id: Int, var name: String)

@Serializable
data class UserCreateRequest(var name: String)

fun Application.configureRouting() {
    routing {
        get<HomeResource> {
            call.respond(ContentResponse(text = "Hello World!"))
        }

        get<UsersResource> {
            val users = transaction {
                Users.selectAll()
                     .map { UserResponse(it[Users.id], it[Users.name]) }
                     .toList()
            }
            call.respond(users)
        }

        post<UsersResource.New> {
            val request = call.receive<UserCreateRequest>()
            val userId = transaction {
                Users.insert {
                    it[name] = request.name
                } get Users.id
            }
            call.respond(UserResponse(userId, request.name))
        }
    }
}
