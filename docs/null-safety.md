# Null-safety

## Regular initialization means non-null by default

```kotlin
fun main() {
  // Regular initialization means non-null by default
  var value: String = "non-null string value" 
  println("String value : $value")
  value = null // compilation error
}
```

## Nullable type

```kotlin
fun main() {
  // If you need a variable that can be null, 
  // declare it nullable by adding ? at the end of its type.
  var value: String? = "nullable string value" 
  println("String value : $value")
  value = null
  println("String value : $value")
}
```

## Working with Collection

```kotlin
fun main() {
  // A Mutable List
  val list = mutableListOf<String>("abc", "def")
  // Add and non-null Value
  list.add("non-null value")

  // Unable to add Null value
  list.add( null ) // compilation error

  // Print Result
  println(list)
}
```

## Working with Nullable value

### Using !! Operator 

With not null value

```kotlin
fun main() {
    val nullableValue: String? = "Nullable value"
    val len = nullableValue!!.length
    println( "length $len" )
}
```

When value is null

```kotlin
fun main() {
  val nullableValue: String? = null
  val len = nullableValue!!.length // Throw NullPointerException
  println( "length $len" )
}
```

### Elvis Operator

With not null value

```kotlin
fun main() {
  var nullableValue: String? = "Nullable value"
  var value : String = nullableValue ?: "default value"
  println("String value : $value")
}
```

With null value

```kotlin
fun main() {
  var nullableValue: String? = null
  var value : String= nullableValue ?: "default value"
  println("String value : $value")
}
```

## Safe casting


```kotlin
fun main() {
  var nullableValue: String? = null
  var value = nullableValue ?: "default value"
  println("String value : $value")
}
```
