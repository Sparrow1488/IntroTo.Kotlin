package models

import kotlinx.serialization.Serializable

@Serializable
data class FileSrc(
    val src: String
)

@Serializable
data class FileSrcCollection(
    val values: List<FileSrc>
)