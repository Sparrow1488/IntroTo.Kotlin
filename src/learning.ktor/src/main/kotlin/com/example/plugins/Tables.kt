package com.example.plugins

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)
    val cityId = (integer("city_id") references Cities.id).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID")
}

object Cities : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Cities_ID")
}