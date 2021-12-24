# Immutable

## Keywords

var keyword for declare a re-assignable variable

```kotlin
fun main() {
  var value = "String variable is declared by var"
  println("String value: $value")
  value = "that means it can be re-assigned"
  println("String value: $value")    
}
```

val keyword for read-only variable, just initialize once, no change

```kotlin
fun main() {
  val value = "String variable is declared by val"  
  println("String value: $value")
  // Compilation failure
  value = "that means it cannot be re-assigned"
  println("String value: $value")    
}
```

## Immutable Collections

Unable to add an Item to List

```kotlin
fun main() {
  val list : List<String> = listOf("first", "second")
  println("List: $list")
  // No add method
  list.add("third")
}
```

If need to change, use Mutable Collection

```kotlin
fun main() {
  val mutableList : MutableList<String> = mutableListOf("first", "second")
  // Mutable List allows to add items
  mutableList.add("third")
  println("Mutable List: $mutableList")
  // Mutable List is still a List
  val list : List<String> = mutableList
  println("List: $list")
}
```

## DTOs

```kotlin
data class Customer(
    val id: String,
    val name: String? = null,
    val email: String? = null
)

fun main() {
  val tmpCustomer = Customer( id = "bcc1f488-4be1-48c4-a3ee-5ad75b365aaa")
  println(tmpCustomer)
  // Update Customer information
  val customer = tmpCustomer.copy( name = "Vu Hoang Viet", email = "vietvuhoang@gmail.com")
  println(customer)
  assert( customer !== tmpCustomer)
}
```



