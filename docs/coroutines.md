# Coroutines

## Getting Started

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking { // this: CoroutineScope
  launch { // launch a new coroutine and continue
      delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
      println("World!") // print after delay
  }
  println("Hello") // main coroutine continues while a previous one is delayed
}
```

## Coroutines Delay

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    
    val start = System.currentTimeMillis()
    
    launch {
        delay(1000L)
        println("[${System.currentTimeMillis() - start}ms] World!")
    }
    launch {
        delay(2000L)
        println("[${System.currentTimeMillis() - start}ms] Welcome to Kotlin")
    }
    
    println("Start working..")
    
}

```

## Thread Sleep

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    
    val start = System.currentTimeMillis()

    launch {
        Thread.sleep(1000L)
        println("[Thread-ID ${Thread.currentThread().id}][${System.currentTimeMillis() - start}ms] World!")
    }
    launch {
        Thread.sleep(2000L)
        println("[Thread-ID ${Thread.currentThread().id}][${System.currentTimeMillis() - start}ms] Welcome to Kotlin!")
    }
    println("Start working..")
    
}
```

## Suspend Function

```kotlin
import kotlinx.coroutines.*

suspend fun <T> forEach( list : List<T>, block : suspend (T) -> Unit ) {
  list.forEach {
      block(it)
  }
}

fun main() = runBlocking {

  val list = listOf( "Long", "Thac Anh", "Viet")

  forEach( list ) {
      println(it)
      delay(1000) // kotlin delay is a suspend function
  }

}
```

## Exception Handling

```kotlin
import kotlinx.coroutines.*

suspend fun <T> forEach( list : List<T>, block : suspend (T) -> Unit ) {
  list.forEach {
      block(it)
  }
}

fun main() = runBlocking {

  val list = listOf( "Long", "Thac Anh", "Viet")

  try {
      forEach( list = list ) {
          if ( it === "Thac Anh") throw IllegalStateException(it)
          println(it)
          delay(100)
      }
  } catch ( e : Exception) {
      e.printStackTrace()
  }
}
```

## Using inline function

```kotlin
import kotlinx.coroutines.*

inline fun <T> forEach( list : List<T>, block : (T) -> Unit ) {
    list.forEach {
        block(it)
    }
}

fun main() = runBlocking {
    val list = listOf( "Long", "Thac Anh", "Viet")
    forEach( list = list ) {
        println(it)
        delay(1000)
    }
}
```

