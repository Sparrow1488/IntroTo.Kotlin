package templates

import com.typesafe.config.Config

data class HelloText(val config: Config) {
    override fun toString(): String {
        val name = config.getString("bot.name")
        val description = config.getString("bot.description")
        val version = config.getString("bot.version")

        return "$name ($version)\n$description"
    }
}
