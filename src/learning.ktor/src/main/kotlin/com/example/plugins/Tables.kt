package com.example.plugins

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Users : IntIdTable() {
    val name: Column<String> = varchar("name", 50)
    val city = reference("city", Cities).nullable()
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(Users)
    var name by Users.name
    var city by CityDAO optionalReferencedOn Users.city
}

object Cities : IntIdTable() {
    val name: Column<String> = varchar("name", 50)
}

class CityDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CityDAO>(Cities)
    var name by Cities.name
}