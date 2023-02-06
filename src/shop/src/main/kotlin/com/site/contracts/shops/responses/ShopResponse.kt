package com.site.contracts.shops.responses

import com.site.contracts.products.responses.ProductResponse
import kotlinx.serialization.Serializable

@Serializable
data class ShopResponse(
    var id: Int,
    var latest: List<ProductResponse>,
    var title: String
)