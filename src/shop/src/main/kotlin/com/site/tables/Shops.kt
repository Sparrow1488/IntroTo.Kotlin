package com.site.tables

import com.site.infrastructure.abstractions.IContractSerializable
import com.site.contracts.shops.responses.ShopResponse
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Shops : IntIdTable("shops") {
    val title = varchar("title", 50)
    val ownerId = reference("owner_id", Users)
}

class ShopDAO(
    id: EntityID<Int>
) : IntEntity(id), IContractSerializable<ShopResponse> {

    companion object : IntEntityClass<ShopDAO>(Shops)

    var title by Shops.title
    val products by ProductDAO referrersOn Products.shopId
    var owner by UserDAO referencedOn Shops.ownerId

    override fun toSerializable(): ShopResponse = ShopResponse(
        this.id.value,
        this.products.take(5).map { it.toSerializable() },
        this.title
    )
}