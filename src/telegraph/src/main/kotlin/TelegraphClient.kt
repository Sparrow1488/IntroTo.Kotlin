import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.TghResponse
import requests.Request

class TelegraphClient {

    private val host:String = "https://api.telegra.ph/"
    private val client = HttpClient(CIO)

    suspend fun sendRequestJson(method: String, request: Request) : String {
        val requestUri = host + method + "?" + request.toUrlParams()
        val response = client.get(requestUri)
        return response.bodyAsText()
    }
}