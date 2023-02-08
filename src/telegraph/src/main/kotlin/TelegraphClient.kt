import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.TghResponse
import requests.Request

class TelegraphClient() {

    private val json:Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    private val host:String = "https://api.telegra.ph/"
    private val client = HttpClient(CIO)

    suspend fun <TResult> sendRequest(method: String, request: Request) : TResult {
        val jsonResponse = sendRequest(method, request)
        val tghResponse = json.decodeFromString<TghResponse<TResult>>(jsonResponse)
        if(!tghResponse.ok || tghResponse.result == null) {
            throw Exception("Invalid Telegraph response")
        }
        return tghResponse.result!!
    }

    suspend fun sendRequest(method: String, request: Request) : String {
        val requestUri = host + method + "?" + request.toUrlParams()
        val response = client.get(requestUri)
        return response.bodyAsText()
    }
}