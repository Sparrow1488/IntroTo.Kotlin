import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.dsl.module

abstract class Base()

open class A(val name: String) : Base() {
    override fun toString() = name
}

class B(val parent: A, name: String) : A(name)

fun main() {
    initKoin()
    val koin = get()
    val service: Base = koin.get()

    println(service.toString())
}

private fun initKoin() : Koin {
    val myModule = module {
        single { A(name = "A service") }
        single<Base> { A(name = "A service") }
        single<Base> { B(get(), name = "B service") }
    }

    startKoin {
        allowOverride(true)
        modules(myModule)
    }

    return get()
}