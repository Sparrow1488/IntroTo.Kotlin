package com.example.routes

import com.example.plugins.CitiesResource
import com.example.plugins.CityDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.sqrt

@Serializable
data class CityCreateRequest(var name: String)

@Serializable
data class CityResponse(var id: Int, var name: String)

fun foo(): Unit {
}

fun Application.configureCitiesRouting() {
    val hello: () -> Unit
    hello = ::foo
    hello()

    routing {
        get<CitiesResource> {
            val cities = transaction {
                CityDAO.all().map {
                    CityResponse(it.id.value, it.name)
                }
            }
            call.respond(cities)
        }

        get<CitiesResource.Id> {
            val city = transaction {
                CityDAO.findById(it.id)
            }

            if(city == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(CityResponse(city.id.value, city.name))
        }

        authenticate("basic") {
            post<CitiesResource.New> {
                val create = call.receive<CityCreateRequest>()
                val city = transaction {
                    CityDAO.new {
                        name = create.name.capitalize()
                    }
                }
                call.respond(CityResponse(city.id.value, city.name))
            }
        }
    }
}