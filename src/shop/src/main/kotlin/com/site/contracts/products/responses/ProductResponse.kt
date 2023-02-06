package com.site.contracts.products.responses

import com.site.contracts.shops.responses.ShopResponse
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    var id: Int,
    var shopId: Int,
    var name: String,
    var description: String?,
    var price: Double
)