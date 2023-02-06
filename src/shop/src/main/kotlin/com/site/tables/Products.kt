package com.site.tables

import com.site.abstractions.IContractSerializable
import com.site.contracts.products.responses.ProductResponse
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable("products") {
    val name = varchar("name", 50)
    val description = varchar("description", 1500).nullable()
    val price = double("price")
    val shop = reference("shop_id", Shops)
}

class ProductDAO(id: EntityID<Int>) : IntEntity(id), IContractSerializable<ProductResponse> {
    companion object : IntEntityClass<ProductDAO>(Products)
    var name by Products.name
    var description by Products.description
    var price by Products.price
    var shop by ShopDAO referencedOn Products.shop

    override fun toSerializable(): ProductResponse = ProductResponse(
        this.id.value,
        this.shop.id.value,
        this.name,
        this.description,
        this.price
    )
}