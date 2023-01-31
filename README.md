## IntroTo.Kotlin

На самом деле Котлин представляет собой экосистему:

Kotlin Common -> Kotlin/(JVM, JS, Native) -> (JVM, JS, Native) Code

Kotlin common (ядро) включает в себя сам язык, основные библиотеки и инструменты для построения программ. Для взаимодействия с конкретной платформой используются специальные версии Котлин (JVM, JS, Native), которые представляет из себя расширение языка. В будущем вся экосистема будет объединена в Kotlin Multiplatform.

### Особенности языка

* Разработан под JVM, что позволяет без проблем использовать в проекте Java библиотеки

* Позволяет писать в разы меньше кода по сравнению с Java, т.к. переполнен синтаксическим сахаром

* Поддерживает парадигмы ООП и функциональщину

* Нулевая безопасность - защищает от использования нулевых ссылок (NullPointerException)

* Smart casts - проверка на null в блоке if и каст переменной к преверяемому типу при `true`

* Корутины - поддержка асинхронного программирования

* Перегрузка операторов

* Companion Object

#### Переменные

**val** - immutable variable

**var** - mutable variable

**nullability** - переменные с возможностью присвоения null

По умолчанию переменным нельзя присвоить значение null. Для этого нужно явно объявить nullability variable

```kotlin
var name: String = null  // fail
var name: String? = null // ok - nullability
```

##### Переменные-функции

Для присвоение переменной функции нужно воспользоваться оператором "::" (указатель?).

```kotlin
fun main() {
    // функция без параметров, которая ничего не возвращает
    val message: () -> Unit 
    message = ::hello // присвоение указателя на функцию
    message()
}
fun hello() = println("Hello Kotlin")
```

#### Объекты

Представлением объекта является **класс**. Класс фактически представляет определение объекта. А объект является конкретным **воплощением класса**.

Все классы наследуются от God-object'а - **Any**. Это динамический тип, в который можно присвоить значение одного типа, а после заменить на другой:

```kotlin
var name: Any = "Yuri"
println(name) // Yuri
name = 200
println(name) // 200
```

#### Создание объектов

Для создания объектов класса существует два вида конструкторов:

* Первичный - находится в заголовке объявления класса

* Вторичный - объявлен внутри тела класса и может иметь несколько экземпляров

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

#### Spread operator

"*" (spread operator) позволяет передать параметру в качестве значения элементы из массива. Проще говоря, чтобы передать в качестве параметра массив со значениями в функцию, принимающая varargs - spread oprator нам в помощь:

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

#### Elvis operator

Делает проверку переменной слева на null. Если на переменную слева отсутсвует ссылка, то присваивается значение справа

```kotlin
var number: Int? = null
var value = number?: 0
```

#### Smart casting

Проверка типа переменной с последующим кастом

```kotlin
fun foo(value: Any) {
    if(value is String) // smart casting
        println("String value = $value")
    if(value is Double) // smart casting
        println("Sqrt of Double value = ${sqrt(value)}")
}
```

#### Scope-functions

**Scope-функции** позволяют выполнять код для некоторого объекта в виде лямбда-выражения.

**let** - функция расширения, которая в качестве параметра `it` получает объект, для которого вызывается функция

```kotlin
val tom = Person(name = "Tom")
tom.name?.let(::println)
tom.name?.let { // it - String? (tom.name)
    println("Hello, $it!")
}
```

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

**apply** - функция-расширения, которая в качестве параметра `this` получает объект, для которого вызывается функция

```kotlin
tom.apply { // this - Person
    email = email?.replace("gmail.com", "yandex.ru")
}
tom.email?.let(::println)
```

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

**also** - функция-расширения, которая в качестве параметра `it` получает объект, для которого вызывается функция

```kotlin
tom.also { // it - Person
    it.email = it.email?.replace("yandex.ru", "mail.ru")
}
tom.email?.let(::println)
```

Разница между `it` и `this` в том, что обращение к переданному объекту в контексте `it` осуществляется через обращение к переменной `it`. Когда в рамках контекста `this` обращение к объекту не делегируется через отдельную переменную `it` (то-есть обращение к свойствам идет напрямую)

```kotlin
tom.apply { // this context
    email = "tom@gmail.com"
}
tom.also { // it context
    it.email = "tom@yandex.ru"
}
```
