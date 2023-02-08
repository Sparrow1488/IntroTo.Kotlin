import constants.TghMethods
import models.Account
import requests.CreateAccountRequest
import requests.CreatePageRequest

class Telegraph {
    private val client: TelegraphClient = TelegraphClient()

    suspend fun createAccount(request: CreateAccountRequest): Account {
        return client.sendRequest<Account>(TghMethods.CREATE_ACCOUNT, request)
    }

    suspend fun createPage(request: CreatePageRequest) : String {
        val response = client.sendRequest(TghMethods.CREATE_PAGE, request)
        return response
    }
}