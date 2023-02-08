package com.site.infrastructure.services.users

import com.site.infrastructure.constants.PrincipalDefaults
import com.site.infrastructure.exceptions.ForbiddenException
import com.site.infrastructure.exceptions.NotFoundException
import com.site.tables.UserDAO
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserSession : IUserSession {
    override fun getUser(call: ApplicationCall): UserDAO {
        val userIdentity = call.principal<JWTPrincipal>()
            ?: throw ForbiddenException("User principal not found", "Failed to receive JWTPrincipal")

        val userId = userIdentity
            .payload
            .getClaim(PrincipalDefaults.ClaimNames.USER_ID).asInt()

        val user = transaction {
            UserDAO.findById(userId)
        } ?: throw NotFoundException("User not found by principal claim id", userId.toString(), "User")

        return user
    }
}