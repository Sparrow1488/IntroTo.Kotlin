import constants.TghMethods
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import models.Account
import models.TghResponse
import requests.CreateAccountRequest
import requests.CreatePageRequest

class Telegraph {

    private val client: TelegraphClient = TelegraphClient()

    @OptIn(ExperimentalSerializationApi::class)
    private val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

    suspend fun createAccount(request: CreateAccountRequest): Account {
        return getResult(client.sendRequestJson(TghMethods.CREATE_ACCOUNT, request))
    }

    suspend fun createPage(request: CreatePageRequest) : String {
        return client.sendRequestJson(TghMethods.CREATE_PAGE, request)
    }

    private inline fun <reified TResult> getResult(jsonResponse: String) : TResult {
        val tghResponse = json.decodeFromString<TghResponse<TResult>>(jsonResponse)
        if(!tghResponse.ok || tghResponse.result == null) {
            throw Exception("Invalid Telegraph response")
        }
        return tghResponse.result!!
    }
}