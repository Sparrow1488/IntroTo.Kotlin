import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import requests.Request
import java.io.File

class TelegraphClient {

    private val baseApiUrl:String = "https://api.telegra.ph/"
    private val client = HttpClient(CIO)

    suspend fun sendRequestJson(method: String, request: Request) : String {
        val requestUri = baseApiUrl + method + "?" + request.toUrlParams()
        val response = client.get(requestUri)
        return response.bodyAsText()
    }

    suspend fun uploadImage(filePath: String) : String {
        val requestUri = "https://telegra.ph/upload"
        val file = File(filePath)
        if(!file.exists()) {
            throw Exception("File not found by path '$filePath'")
        }
        val response = client.submitFormWithBinaryData(
            url = requestUri,
            formData = formData {
                append("filename", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                })
            }
        )
        return response.bodyAsText()
    }
}