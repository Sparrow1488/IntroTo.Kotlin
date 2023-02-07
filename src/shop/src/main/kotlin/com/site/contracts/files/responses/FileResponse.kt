package com.site.contracts.files.responses

import kotlinx.serialization.Serializable

@Serializable
data class FileResponse(
    val id: Int,
    val fullName: String,
    val mimeType: String,
    val urls: HashMap<String, String>
)