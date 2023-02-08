package com.site.infrastructure.services.storages

import com.site.tables.FileDAO
import io.ktor.http.content.*

interface IFileUploader {
    fun upload(file: PartData.FileItem) : FileDAO
}