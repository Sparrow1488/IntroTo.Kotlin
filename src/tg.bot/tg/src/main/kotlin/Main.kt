import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.*
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.entities.*
import com.typesafe.config.Config
import org.koin.core.context.GlobalContext.get
import plugin.configureDependencyInjection
import routes.configureMediaRouting
import routes.configureNavigation
import routes.configureStartCommand
import java.io.File

fun main() {
    configureDependencyInjection()
    val koin = get()

    val config: Config = koin.get()
    val accessToken = config.getString("telegram.bot.token")
    if (accessToken.isNullOrEmpty()) {
        throw Exception("No access token in file")
    }

    val bot = bot {
        token = accessToken
        dispatch {
            configureStartCommand()
            configureNavigation()
            configureMediaRouting()
        }
    }
    bot.startPolling()
}