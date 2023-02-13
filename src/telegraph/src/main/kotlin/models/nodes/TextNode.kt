package models.nodes

import models.Attribute

class TextNode(
    text: String,
) : Node("p", Attribute(""), listOf(text))