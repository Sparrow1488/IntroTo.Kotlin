import constants.TghMethods
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import models.Account
import models.FileSrc
import models.PageInfo
import models.TghResponse
import requests.CreateAccountRequest
import requests.CreatePageRequest

class Telegraph {

    val client: TelegraphClient = TelegraphClient()

    @OptIn(ExperimentalSerializationApi::class)
    private val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

    suspend fun uploadImage(filePath: String) : FileSrc {
        return getObject(
            client.uploadImage(filePath)
                .replace("[", "") // TODO: can't parse "[ { } ]"
                .replace("]", "")
        )
    }

    suspend fun createAccount(request: CreateAccountRequest): Account {
        return getResponseObject(client.sendRequestJson(TghMethods.CREATE_ACCOUNT, request))
    }

    suspend fun createPage(request: CreatePageRequest) : PageInfo {
        return getResponseObject(client.sendRequestJson(TghMethods.CREATE_PAGE, request))
    }

    private inline fun <reified TResult> getResponseObject(jsonResponse: String) : TResult {
        val tghResponse = getObject<TghResponse<TResult>>(jsonResponse)
        if(!tghResponse.ok || tghResponse.result == null) {
            throw Exception("Invalid Telegraph response. Json response:\n$jsonResponse")
        }
        return tghResponse.result!!
    }

    private inline fun <reified TResult> getObject(jsonResponse: String) : TResult {
        return json.decodeFromString(jsonResponse)
    }
}