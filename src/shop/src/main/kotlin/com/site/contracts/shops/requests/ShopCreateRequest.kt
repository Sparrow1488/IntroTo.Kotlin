package com.site.contracts.shops.requests

import kotlinx.serialization.Serializable

@Serializable
data class ShopCreateRequest(
    var title: String
)