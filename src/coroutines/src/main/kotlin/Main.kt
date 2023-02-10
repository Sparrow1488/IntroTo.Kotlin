import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    helloWorld()
    println("Done")
    startLongOperation()
}

suspend fun helloWorld() = coroutineScope {
    val helloZero = launch {
        delay(500)
        println("World 0")
    }
    launch {
        delay(500)
        println("World 1")
    }
    launch {
        delay(1000)
        println("World 2")
    }
    println("Hello")
    helloZero.join()
}

suspend fun startLongOperation() = coroutineScope {
    print("loading")

    var counter:Long = 20
    repeat(50) {
        launch { // Not a thread!
            delay(counter)
            print(".")
            counter += 5
        }.join()
    }
}