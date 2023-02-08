package com.site.infrastructure.services.storages

import com.site.tables.FileDAO
import com.site.tables.FileUrlDAO
import io.ktor.http.content.*
import org.jetbrains.exposed.sql.transactions.transaction

class FilesStorage : IFileUploader {
    override fun upload(file: PartData.FileItem): FileDAO {
        val name = file.originalFileName!!
        // TODO: fix file save
//        val fileCopy = File("static/files/$name") // TODO: name should be unique generated
//
//        file.streamProvider().use { its ->
//            fileCopy.outputStream().buffered().use {
//                its.copyTo(it)
//            }
//        }

        val dbFile = transaction {
            val dbFile = FileDAO.new {
                fullName = name
                mimeType = file.contentType?.toString() ?: "undefined"
            }
            FileUrlDAO.new {
                value = "http://127.0.0.1:8080/static/uploads/$name"
                description = "original"
                this.file = dbFile
            }
            dbFile
        }
        return dbFile
    }
}