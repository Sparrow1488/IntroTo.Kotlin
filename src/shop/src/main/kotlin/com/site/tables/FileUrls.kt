package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object FileUrls : IntIdTable("file_urls") {
    val value = varchar("value", 500)
    val description = varchar("description", 100).nullable() // TODO: change to enum "Type" (preview, original)
    val fileId = reference("file_id", Files)
}

class FileUrlDAO(
    id: EntityID<Int>
) : IntEntity(id) {

    companion object : IntEntityClass<FileUrlDAO>(FileUrls)

    var value by FileUrls.value
    var description by FileUrls.description
    var file by FileDAO referencedOn FileUrls.fileId
}