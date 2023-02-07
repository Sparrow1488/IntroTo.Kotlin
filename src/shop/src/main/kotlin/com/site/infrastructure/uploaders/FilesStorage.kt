package com.site.infrastructure.uploaders

import com.site.tables.FileDAO
import com.site.tables.FileUrlDAO
import io.ktor.http.content.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import javax.naming.spi.DirectoryManager

class FilesStorage : IFileUploader {
    override fun upload(uploadedFile: PartData.FileItem): FileDAO {
        val name = uploadedFile.originalFileName!!
        val fileCopy = File("./static/uploads/$name")

        uploadedFile.streamProvider().use { its ->
            fileCopy.outputStream().buffered().use {
                its.copyTo(it)
            }
        }

        val dbFile = transaction {
            val dbFile = FileDAO.new {
                fullName = name
                mimeType = "mime/${uploadedFile.contentType}"
            }
            FileUrlDAO.new {
                value = "http://127.0.0.1:8080/static/uploads/$name"
                description = "original"
                file = dbFile
            }
            dbFile
        }
        return dbFile
    }
}