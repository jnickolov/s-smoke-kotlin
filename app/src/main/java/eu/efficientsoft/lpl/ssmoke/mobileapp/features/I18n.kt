package eu.efficientsoft.lpl.ssmoke.mobileapp.features

import kotlinx.serialization.Serializable

@Serializable
class I18n (val code: String, val props: Map<String, String>) {
    fun prop (key: String) = props[key] ?: key
}

enum class SSmokeLangKey {
    BG,
    EN;

    // toString is the same:
//    fun asString() {
//        when (this) {
//            EN -> "EN"
//            BG -> "BG"
//            else -> "UNKNOWN"
//        }
//    }
}