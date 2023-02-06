package com.site.contracts.products.requests

import kotlinx.serialization.Serializable

@Serializable
data class ProductCreateRequest(
    var shopId: Int,
    var name: String,
    var description: String,
    var price: Double
)