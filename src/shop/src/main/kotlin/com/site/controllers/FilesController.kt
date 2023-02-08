package com.site.controllers

import com.site.contracts.files.responses.FileResponse
import com.site.infrastructure.services.storages.FilesStorage
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.configureFilesRouting() = route("/files") {
    val storage = FilesStorage()

    post {
        val part = call.receiveMultipart()
        val filesList = mutableListOf<FileResponse>()
        part.forEachPart { part ->
            if(part is PartData.FileItem) {
                val file = storage.upload(part)
                val fileResponse = transaction {
                    file.toSerializable()
                }
                filesList.plusAssign(fileResponse)
            }
        }
        call.respond(filesList)
    }
}