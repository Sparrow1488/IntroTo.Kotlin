package com.example.routes

import com.example.plugins.CitiesResource
import com.example.plugins.City
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

@Serializable
data class CityCreateRequest(var name: String)

@Serializable
data class CityResponse(var id: Int, var name: String)

fun Application.configureCitiesRouting() {
    routing {
        get<CitiesResource> {
            val cities = transaction {
                City.all().map {
                    CityResponse(it.id.value, it.name)
                }
            }
            call.respond(cities)
        }

        get<CitiesResource.Id> {
            val city = transaction {
                City.findById(it.id)
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
                    City.new {
                        name = create.name.capitalize()
                    }
                }
                call.respond(CityResponse(city.id.value, city.name))
            }
        }
    }
}