package requests

import abstractions.ISerializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.nodes.Node

class ContentRequest(
    private val nodes: List<Node>
) : Request(), ISerializable {

    override fun toUrlParams(): String {
        return encodeParams(
            hashMapOf("content" to toJson())
        )
    }

    override fun toJson(): String {
        return Json.encodeToString(nodes)
    }
}