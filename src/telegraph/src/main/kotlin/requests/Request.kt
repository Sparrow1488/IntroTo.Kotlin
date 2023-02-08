package requests

import utils.StringUtils.encodeUtf8

abstract class Request() {
    abstract fun toUrlParams() : String

    protected fun encodeParams(params: HashMap<String, String>): String =
        params.map {(k, v) -> "${(k.utf8())}=${v.utf8()}"}.joinToString("&")

    private fun String.utf8() : String = encodeUtf8(this)
}