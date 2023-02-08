package models

import kotlinx.serialization.Serializable

@Serializable
class TghResponse<TResult> {
    var ok: Boolean = false
    var result:TResult? = null
}