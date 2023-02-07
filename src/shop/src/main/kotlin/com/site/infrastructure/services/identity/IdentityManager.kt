package com.site.infrastructure.services.identity

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.site.infrastructure.constants.AppClaims
import com.site.contracts.users.requests.UserCreateRequest
import com.site.infrastructure.constants.SecurityConfig
import com.site.contracts.users.requests.UserLoginRequest
import com.site.infrastructure.exceptions.BadRequestException
import com.site.infrastructure.services.hashers.IHasher
import com.site.infrastructure.services.hashers.Sha256Hasher
import com.site.tables.UserCredentials
import com.site.tables.UserCredentialsDAO
import com.site.tables.UserDAO
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.MessageDigest
import java.util.*

class IdentityManager : IIdentityManager {
    private val hasher: IHasher

    init {
        hasher = Sha256Hasher()
    }

    override fun RegisterUser(request: UserCreateRequest): String {
        checkUniqueCredentials(request)

        val credentials = transaction {
            UserCredentialsDAO.new {
                login = request.login
                hashedPassword = hasher.hash(request.password)
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
        val hashedPassword = hasher.hash(request.password)
        val existsCredentials = transaction {
            UserCredentialsDAO.find {
                UserCredentials.login eq request.login
                UserCredentials.hashedPassword eq hashedPassword
            }.firstOrNull()
        } ?: throw BadRequestException("Invalid login&password credentials")

        return createToken(existsCredentials.id.value, existsCredentials.login)
    }

    private fun checkUniqueCredentials(request: UserCreateRequest) {
        val anyExists = transaction {
            UserCredentialsDAO.find { UserCredentials.login eq request.login }.any()
        }
        if(anyExists)
            throw BadRequestException("User with current login already exists")
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