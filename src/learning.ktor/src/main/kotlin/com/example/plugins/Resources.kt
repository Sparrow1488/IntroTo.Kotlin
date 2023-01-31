package com.example.plugins

import io.ktor.resources.*

@Resource("/")
class HomeResource()

@Resource("/cities")
class CitiesResource() {
    @Resource("new")
    class New(var parent: CitiesResource = CitiesResource())

    @Resource("{id}")
    class Id(var parent: CitiesResource = CitiesResource(), var id: Int)
}

@Resource("/users")
class UsersResource() {
    @Resource("new")
    class New(var parent : UsersResource = UsersResource())

    @Resource("{id}")
    class Id(var parent : UsersResource = UsersResource(), var id: Int) {
        @Resource("edit")
        class Edit(var parent : Id) {

        }
    }
}