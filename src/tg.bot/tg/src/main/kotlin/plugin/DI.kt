package plugin

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

fun configureDependencyInjection() {
    val config = ConfigFactory.load()

    startKoin {
        val appModule = module {
            single<Config> { config }
        }
        modules(appModule)
    }
}