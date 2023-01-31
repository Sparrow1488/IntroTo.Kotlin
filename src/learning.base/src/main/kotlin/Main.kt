fun main() {
    println("Hello World!")

    // Scope-function: let
    val tom = Person("Tom")
    val unit = tom.name?.let { println("Person named $it") } // Print "Tom" and return Unit

    // Invocation context parameter - [it]
    val anna = Person(null)
    val helloAnna = anna.name?.let {
        "Hello, $it!"
    } // return null

    // Scope-function: with
    // Take parameter (Person scope) - [this]
    val tomEmail = with(tom) {
        if(!tom.name.isNullOrEmpty())
            email = "${this.name}@gmail.com"
        email
    }
    tomEmail?.let(::println) // Print Tom email


    // Scope-function: run (as extension)
    val validationResult = tom.run {
        if(!tom.name.isNullOrEmpty() && !tom.email.isNullOrEmpty())
            "valid"
        else "invalid"
    }
    validationResult.run(::println) // Print "valid"


    // Scope-function: apply
    tom.apply {
        email = email?.replace("gmail.com", "yandex.ru")
    }
    tom.email?.let(::println)

    // Scope-function: also
    tom.also {
        it.email = it.email?.replace("yandex.ru", "mail.ru")
    }
    tom.email?.let(::println)
}

data class Person(val name: String?, var email: String? = null)