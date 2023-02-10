package models

import kotlinx.serialization.Serializable

@Serializable
class PageInfo(
    val path: String,
    val url: String,
    val title: String,
    val description: String,
    val authorName: String,
    val views: Int,
    val canEdit: Boolean
) // val content: ...