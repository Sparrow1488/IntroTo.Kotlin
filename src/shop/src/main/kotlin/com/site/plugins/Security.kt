package com.site.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.site.infrastructure.constants.PrincipalDefaults
import com.site.infrastructure.constants.SecurityConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val config = SecurityConfig()
    install(Authentication) {
        jwt {
            validate { credential ->
                val username = credential.payload.getClaim(PrincipalDefaults.ClaimNames.USERNAME).asString()
                if (!username.isNullOrEmpty())
                    return@validate JWTPrincipal(credential.payload)
                return@validate null
            }

            realm = config.realm
            verifier(
                JWT
                .require(Algorithm.HMAC256(config.secret))
                .withAudience(config.audience)
                .withIssuer(config.issuer)
                .build())
        }
    }
}
