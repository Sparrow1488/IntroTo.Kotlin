package com.site.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.site.constants.AppClaims
import com.site.contracts.users.requests.UserCreateRequest
import com.site.constants.SecurityConfig
import com.site.contracts.users.requests.UserLoginRequest
import com.site.tables.UserCredentials
import com.site.tables.UserCredentialsDAO
import com.site.tables.UserDAO
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class IdentityManager : IIdentityManager {
    override fun RegisterUser(request: UserCreateRequest): String {
        checkUniqueCredentials(request)

        val credentials = transaction {
            UserCredentialsDAO.new {
                login = request.login
                hashedPassword = request.password // TODO: hash password
                email = request.email
                phone = request.phone
                user = UserDAO.new {
                    firstName = request.firstName
                    lastName = request.lastName
                }
            }
        }

        return createToken(credentials.id.value, credentials.login)
    }

    override fun LoginUser(request: UserLoginRequest): String {
        val existsCredentials = transaction {
            UserCredentialsDAO.find {
                UserCredentials.login eq request.login
//                UserCredentials.hashedPassword eq request.password // TODO: hash password
            }.firstOrNull()
        } ?: throw Exception("Invalid credentials")

        return createToken(existsCredentials.id.value, existsCredentials.login)
    }

    private fun checkUniqueCredentials(request: UserCreateRequest) {
        val anyExists = transaction {
            UserCredentialsDAO.find { UserCredentials.login eq request.login }.any()
        }
        if(anyExists)
            throw Exception("User with current login already exists")
    }

    private fun createToken(id: Int, login: String) : String {
        val config = SecurityConfig()
        return JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withClaim(AppClaims.userId, id)
            .withClaim(AppClaims.username, login)
            .withExpiresAt(Date(System.currentTimeMillis() + 600000))
            .sign(Algorithm.HMAC256(config.secret))
    }
}