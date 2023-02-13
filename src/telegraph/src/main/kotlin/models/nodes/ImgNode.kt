package models.nodes

import models.Attribute

class ImgNode(
    src: String
) : Node("img", Attribute(src), null)