package requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    private val shortName: String,
    private val authorName: String,
    private val authorUrl: String
) : Request() {

    override fun toUrlParams(): String {
        return encodeParams(
            hashMapOf(
                "short_name" to shortName,
                "author_name" to authorName,
                "author_url" to authorUrl,
            )
        )
    }
}