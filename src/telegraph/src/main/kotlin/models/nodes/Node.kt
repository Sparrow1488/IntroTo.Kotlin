package models.nodes

import kotlinx.serialization.Serializable
import models.Attribute

@Serializable
open class Node(
    val tag: String,
    val attrs: Attribute,
    val children: List<String>?
)