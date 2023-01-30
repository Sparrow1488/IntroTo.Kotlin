package com.example.routes

import com.example.plugins.Cities
import com.example.plugins.CitiesResource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class CityCreateRequest(var name: String)

@Serializable
data class CityResponse(var id: Int, var name: String)

fun Application.configureCitiesRouting() {
    routing {
        get<CitiesResource.Id> {
            val city = transaction {
                Cities
                    .select { Cities.id eq it.id }
                    .map { CityResponse(it[Cities.id], it[Cities.name]) }
                    .firstOrNull()
            }

            if(city == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(city)
        }

        authenticate("basic") {
            post<CitiesResource.New> {
                val create = call.receive<CityCreateRequest>()
                val cityId = transaction {
                    Cities.insert {
                        it[name] = create.name.capitalize()
                    }
                } get Cities.id
                call.respond(CityResponse(cityId, create.name))
            }
        }
    }
}