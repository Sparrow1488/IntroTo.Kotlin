package com.site.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ProductFiles : IntIdTable("product_files") {
    val fileId = reference("file_id", Files)
    val productId = reference("product_id", Products)
}

class ProductFileDAO(
    id: EntityID<Int>
) : IntEntity(id) {

    companion object : IntEntityClass<ProductFileDAO>(ProductFiles)

    var file by FileDAO referencedOn ProductFiles.fileId
    var product by ProductDAO referencedOn ProductFiles.productId
}