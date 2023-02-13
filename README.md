## IntroTo.Kotlin

## Kotlin

### Intro

На самом деле Котлин представляет собой экосистему:

Kotlin Common -> Kotlin/(JVM, JS, Native) -> (JVM, JS, Native) Code

Kotlin common (ядро) включает в себя сам язык, основные библиотеки и инструменты для построения программ. Для взаимодействия с конкретной платформой используются специальные версии Котлин (JVM, JS, Native), которые представляет из себя расширение языка. В будущем вся экосистема будет объединена в Kotlin Multiplatform.

### Features

* Разработан под JVM, что позволяет без проблем использовать в проекте Java библиотеки

* Позволяет писать в разы меньше кода по сравнению с Java, т.к. переполнен синтаксическим сахаром

* Поддерживает парадигмы ООП и функциональщину

* Нулевая безопасность - защищает от использования нулевых ссылок (NullPointerException)

* Smart casts - проверка на null в блоке if и каст переменной к преверяемому типу при `true`

* Корутины - поддержка асинхронного программирования

* Перегрузка операторов

* Companion Object

## Base

### Types

* Numbers and their unsigned: ....

* Booleans

* Characters

* Strings

* Arrays: .......

### Variables

**val** - immutable variable.

**var** - mutable variable.

**nullability** - переменные с возможностью присвоения null.

По умолчанию переменным нельзя присвоить значение `null`. Для этого нужно явно объявить `nullability` variable.

```kotlin
var name: String = null  // fail
var name: String? = null // ok - nullability
```

### Function variables

Для присвоение переменной функции нужно воспользоваться оператором `::`.

```kotlin
fun main() {
    // функция без параметров, которая ничего не возвращает
    val message: () -> Unit 
    message = ::hello // присвоение указателя на функцию
    message()
}
fun hello() = println("Hello Kotlin")
```

### Lambda-functions

```kotlin
fun foo(fuck: String.() -> Unit) {
    // ...logic
    fuck("Some string ref")
    // ...logic
}

fun foo2(fuck: (name: String, age: Int) -> Boolean) : Boolean {
    // ...logic
    return fuck("Some name", 20)
    // ...logic
}
```

### Spread operator

Spread (`*`) позволяет передать параметру в качестве значения элементы из массива. Проще говоря, чтобы передать в качестве параметра массив со значениями в функцию, принимающая varargs - spread oprator нам в помощь:

```kotlin
fun main() {
    val nums = intArrayOf(1, 2, 3, 4)
    changeNumbers(*nums, koef=2)
}

fun changeNumbers(vararg numbers: Int, koef: Int) {
    for(number in numbers)
        println(number * koef)
}
```

### Elvis operator

Elvis (`?:`) делает проверку переменной слева на `null`. Если на переменную слева отсутсвует ссылка, то присваивается значение справа:

```kotlin
var number: Int? = null
var value = number?: 0
```

### Smart casting

Проверка типа переменной с последующим кастом

```kotlin
fun foo(value: Any) {
    if(value is String) // smart casting
        println("String value = $value")
    if(value is Double) // smart casting
        println("Sqrt of Double value = ${sqrt(value)}")
}
```

### Scope-functions

**Scope-функции** позволяют выполнять код для некоторого объекта в виде лямбда-выражения.

| Function | Object reference | Return value   | Is extension function                        |
| -------- | ---------------- | -------------- | -------------------------------------------- |
| `let`    | `it`             | Lambda result  | Yes                                          |
| `run`    | `this`           | Lambda result  | Yes                                          |
| `run`    | -                | Lambda result  | No: called without the context object        |
| `with`   | `this`           | Lambda result  | No: takes the context object as an argument. |
| `apply`  | `this`           | Context object | Yes                                          |
| `also`   | `it`             | Context object | Yes                                          |

#### let

**let** - функция расширения, которая в качестве параметра `it` получает объект, для которого вызывается функция

```kotlin
val tom = Person(name = "Tom")
tom.name?.let(::println)
tom.name?.let { // it - String? (tom.name)
    println("Hello, $it!")
}
```

Use for:

- Executing a lambda on non-null objects: `let`

- Introducing an expression as a variable in local scope: `let`

#### run

**run** - функция-расширения (либо лямбда-выражение), которая в качестве параметра `this` получет объект, для которого вызывается функция

```kotlin
val tom = Person("Tom")
tom.name.run { // this - Person
    email = "$name@gmail.com"
}
run {
    if(!tom.email.isNullOrEmpty()) "valid"
    else "invalid"
}.let(::println)
```

Use for:

- Object configuration and computing the result: `run`

- Running statements where an expression is required: non-extension `run`

#### apply

**apply** - функция-расширения, которая в качестве параметра `this` получает объект, для которого вызывается функция

```kotlin
tom.apply { // this - Person
    email = email?.replace("gmail.com", "yandex.ru")
}
tom.email?.let(::println)
```

Use for:

- Object configuration: `apply`

#### with

**with** - лямда-выражение, которая в качестве параметра `this` получет объект, для которого вызывается функция

```kotlin
val tom = Person("Tom")
val tomEmail = with(tom) { // this - Person
    if(!tom.name.isNullOrEmpty())
        email = "${this.name}@gmail.com"
    email
}
tomEmail?.let(::println) // Print Tom email
```

Use for:

- Grouping function calls on an object: `with`

#### also

**also** - функция-расширения, которая в качестве параметра `it` получает объект, для которого вызывается функция

```kotlin
tom.also { // it - Person
    it.email = it.email?.replace("yandex.ru", "mail.ru")
}
tom.email?.let(::println)
```

Use for:

- Additional effects: `also`

#### IT vs THIS

Разница между `it` и `this` контекстами в том, что обращение к переданному объекту в контексте `it` осуществляется через обращение к переменной `it`. Когда в рамках контекста `this` обращение к объекту не делегируется через отдельную переменную `it` (то-есть обращение к свойствам идет напрямую)

```kotlin
tom.apply { // this context
    email = "tom@gmail.com"
}
tom.also { // it context
    it.email = "tom@yandex.ru"
}
```

## Object-Oriented-Programming (OOP)

#### Объекты

Представлением объекта является **класс**. Класс фактически представляет определение объекта. А объект является конкретным **воплощением класса**.

Все классы наследуются от God-object'а - **Any**. Это тип, в который можно присвоить значение одного типа, а после заменить на другой:

```kotlin
var name: Any = "Yuri"
println(name) // Yuri
name = 200
println(name) // 200
```

#### Создание объектов

Для создания объектов класса используются конструкторы.

#### Constructors

Существует 2 типа конструкторов:

- **primary** (первичный / обязательный).

- **secondary** (вторичный).

Чтобы добавить какую-либо логику в **primary**-конструктор можно воспользоваться блоком `init`.

Порядок инициализации объекта класса:

> primary → secondary → init block

#### Init block

Поскольку primary-конструктор класса не имеет тела, для обработки входных параметров можно воспользоваться блоком `init`:

```kotlin
class Person(name: String) {
    init {
        if(name.isNullOrEmpty()) {
            throw ArgumentException("You loh")
        }
    }
}
```

#### Типы

**Any** - главный тип, от которого наследуются все доступные типы в Kotlin (object в C#)

**Unit** - специальный тип, который представляет одно значение - объект Unit или void в C#. Используется для упрощения работы с джинериками

**Nothing** - это тип, который используется для объявления функции, которая ничего не возвращает и никогда не заканчивается (например, вечный цикл)

```kotlin
fun doNothingForever(): Nothing {
    while(true) { 
        // code here 
    }
}
```

## Coroutines

### Intro

**Корутины** - это легковесная сопрограмма, позволяющая выполнять несколько блоков кода одновременно. Они очень похожи на потоки, однако таковыми не являются. На их создание, в отличии от потоков, практически не требуется ресурсов.

### Suspend functions

Suspending functions are merely standard Kotlin functions with the suspend modifier added, indicating that they can suspend coroutine execution without blocking the current thread.

В функцию с модификатором `suspend` после компиляции в качестве аргумента добавляется объект `Continuation<T>`, который, по сути своей, является callback функцией.

```kotlin
// Kotlin
suspend fun createPost(token: Token, item: Item): Post { }
// Compiled in Java callback:
Object createPost(Token token, Item item, Continuation<Post> cont) { }
```

`Continuation<T>` это интерфейс, предоставляющий продолжение после точки остоновки, которая возвращает значение `T`.

Содержит две callback-функции:

```kotlin
fun resume(result: T) // возвращает результат
fun resumeWithException(ex: Throwable) // возвращает исключение
```

И по итогу компилируется в машину состояния (код лучше погуглить).

### Coroutine builders

Для создании корутины нужно ключевое слово `suspend` на функции, а тело функции должно быть в `CoroutineContext`, получить который можно с помощью функций `runBlocking` и `coroutineScope`, которые являются корутин билдерами.

#### runBlocking

`runBlocking` является «мостом» между функцией без ключевого слова suspend и функцией поддерживающей ожидаение. Предоставляет `CoroutineScope` для вызова `suspend` функций.

#### coroutineScope

`coroutineScope` предоставляет `CoroutineScope` и может вызываться только внутри функции с ключевым словом `suspend`. 

### Concurrency

???

#### launch

`lauch` также является корутин билдером. Запускает новую сопрограмму, которая выполняется одновременно с остальной частью кода.

Корутины и потоки не одно и то же. Поэтому на создание 100.000 `launch`-функций не потребуется много памяти, нежели, если бы мы работали с `Thread`.

```kotlin
fun main() = runBlocking {
    helloWorld()
    println("Done")
    startLongOperation()
}
// Concurrently executes three sections
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
```

### Dispatchers

?????

# 
