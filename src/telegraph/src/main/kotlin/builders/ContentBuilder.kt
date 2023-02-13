package builders

import models.nodes.ImgNode
import models.nodes.Node
import models.nodes.TextNode
import requests.ContentRequest

class ContentBuilder {
    private val nodes: MutableList<Node> = mutableListOf()

    fun addParagraph(text: String) = nodes.plusAssign(TextNode(text))
    fun addImage(uri: String) = nodes.plusAssign(ImgNode(uri))
    fun buildRequest() : ContentRequest = ContentRequest(nodes)
}