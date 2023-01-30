package com.example.routes

import com.example.contracts.*
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.resources.post
import io.ktor.server.resources.get
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureUsersRouting() = routing {
    get<UsersResource> {
        val users = transaction {
            Users.selectAll()
                .map { UserResponse(it[Users.id], it[Users.name]) }
                .toList()
        }
        call.respond(users)
    }

    authenticate("basic") {
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