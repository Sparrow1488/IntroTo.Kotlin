package models

import kotlinx.serialization.Serializable

@Serializable
class Account(
    val shortName: String,
    val authorName: String,
    val authorUrl: String,
    val accessToken: String,
    val authUrl: String
)