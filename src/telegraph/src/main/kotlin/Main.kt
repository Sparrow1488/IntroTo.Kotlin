import kotlinx.serialization.Serializable
import models.Attribute
import models.ImgNode
import models.Node
import requests.ContentRequest
import requests.CreateAccountRequest
import requests.CreatePageRequest

// TODO:
// 1. + Json serialization
// 2. Request builders

@Serializable
data class Test(val a: String)

suspend fun main() {
    val newAccount = CreateAccountRequest(
        "sparrow",
        "Sparrow1488",
        ""
    )

    val telegraph = Telegraph()
    val account = telegraph.createAccount(newAccount)
    println(account)

    val content = ContentRequest(
        listOf(
            ImgNode("https://telegra.ph//file/0c44063aef409194f3cb0.jpg")
        )
    )
    val newPage = CreatePageRequest(
        "d3b25feccb89e508a9114afb82aa421fe2a9712b963b387cc5ad71e58722",
        "Test",
        "Sprw14",
        "",
        content,
        true
    )
    val page = telegraph.createPage(newPage)
    println(page)
}
