package models

import kotlinx.serialization.Serializable

@Serializable
open class Node(
    val tag: String,
    val attrs: Attribute,
    val children: List<String>?
)

@Serializable
class NodeElement(
    val body: String
)