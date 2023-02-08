package requests

class CreatePageRequest(
    val accessToken: String,
    val title: String,
    val authorName: String,
    val authorUrl: String,
    val content: ContentRequest,
    val returnContent: Boolean
) : Request() {
    override fun toUrlParams(): String {
        return encodeParams(
            hashMapOf(
                "access_token" to accessToken,
                "title" to title,
                "author_name" to authorName,
                "author_url" to authorUrl,
                "content" to content.toJson(),
                "return_content" to returnContent.toString()
            )
        )
    }
}