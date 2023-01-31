package com.example.routes

import com.example.contracts.*
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.resources.post
import io.ktor.server.resources.get
import io.ktor.server.resources.put
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

fun Application.configureUsersRouting() = routing {
    get<UsersResource> {
        val users = transaction {
            User.all().map {
                UserResponse(it.id.value, it.name)
            }
        }
        call.respond(users)
    }

    authenticate("basic") {
        post<UsersResource.New> {
            val request = call.receive<UserCreateRequest>()
            val user = transaction {
                User.new {
                    name = request.name
                }
            }
            call.respond(UserResponse(user.id.value, user.name))
        }

        put<UsersResource.Id.Edit> {
            val request = call.receive<UserEditRequest>()
            val user = transaction {
                val editUser = User.findById(it.parent.id) ?: throw Exception("User not found")
                val existsCity = City.findById(request.cityId) ?: throw Exception("City not found")
                editUser.city = existsCity
                editUser
            }
            call.respond(UserResponse(user.id.value, user.name))
        }
    }
}