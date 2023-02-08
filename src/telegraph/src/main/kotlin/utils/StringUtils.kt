package utils

import java.net.URLEncoder

object StringUtils {
    fun encodeUtf8(input: String): String = URLEncoder.encode(input, "UTF-8")
}