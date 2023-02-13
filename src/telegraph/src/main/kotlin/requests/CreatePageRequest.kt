package requests

class CreatePageRequest(
    private val accessToken: String,
    private val title: String,
    private val authorName: String,
    private val authorUrl: String,
    private val content: ContentRequest,
    private val returnContent: Boolean
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