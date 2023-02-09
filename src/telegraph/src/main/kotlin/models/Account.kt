package models

import kotlinx.serialization.Serializable

@Serializable
class Account(
    val shortName: String,
    val authorName: String,
    val accessToken: String,
) {
    // authUrl: String,
    // authorUrl: String
}