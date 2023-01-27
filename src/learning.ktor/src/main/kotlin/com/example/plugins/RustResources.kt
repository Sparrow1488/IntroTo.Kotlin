package com.example.plugins

import io.ktor.resources.*

@Resource("/")
class HomeResource() {

}

@Resource("/users")
class UsersResource() {
    @Resource("new")
    class New(var parent : UsersResource = UsersResource())
}