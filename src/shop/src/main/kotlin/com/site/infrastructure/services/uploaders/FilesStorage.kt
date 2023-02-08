package com.site.infrastructure.services.uploaders

import com.site.tables.FileDAO
import com.site.tables.FileUrlDAO
import io.ktor.http.content.*
import org.jetbrains.exposed.sql.transactions.transaction

class FilesStorage : IFileUploader {
    override fun upload(file: PartData.FileItem): FileDAO {
        val name = file.originalFileName!!
//        val fileCopy = File("static/files/$name")
//
//        file.streamProvider().use { its ->
//            fileCopy.outputStream().buffered().use {
//                its.copyTo(it)
//            }
//        }

        val dbFile = transaction {
            val dbFile = FileDAO.new {
                fullName = name
                mimeType = file.contentType?.contentType ?: "undefined"
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