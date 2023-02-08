package com.site.tables

import com.site.contracts.files.responses.FileResponse
import com.site.infrastructure.abstractions.IContractSerializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Files : IntIdTable("files") {
    val fullName = varchar("full_name", 100)
    val mimeType = varchar("mime_type", 20)
}

class FileDAO(id: EntityID<Int>) : IntEntity(id), IContractSerializable<FileResponse> {
    companion object : IntEntityClass<FileDAO>(Files)
    var fullName by Files.fullName
    var mimeType by Files.mimeType
    val urls by FileUrlDAO referrersOn FileUrls.fileId
    override fun toSerializable() = FileResponse(
        id.value,
        fullName,
        mimeType,
        hashMapOf(*urls.map { Pair(it.description!!, it.value) }.toTypedArray())
    )
}