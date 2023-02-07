package com.site.infrastructure.services.users

import com.site.tables.UserDAO
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

interface IUserSession {
    fun getUser(call: ApplicationCall) : UserDAO
}