# Kotlin Function

## Lambda function

```kotlin
data class OrderItem(
    val productId: String,
    val amount: Amount,
    val quantity: Int = 1
)

fun List<OrderItem>.total(currency: Currency) : Amount =
    this.filter { it.amount.currency == currency }
        .map { it.amount * it.quantity }
        .fold( initial = Amount( currency = currency, value = BigDecimal.ZERO)) {
            acc, amount -> acc + amount
        }

```

```kotlin
internal class OrderItemTest {

  private companion object {
    val VND : Currency = Currency.getInstance("VND")
  }

  @Test
  fun `calculate total amount of OrderItem List`() {
    val firstItem = OrderItem(
        productId = UUID.randomUUID().toString(),
        amount = Amount( currency = USD, value = 10),
        quantity = 2
    )
    val secondItem = OrderItem(
        productId = UUID.randomUUID().toString(),
        amount = Amount( currency = VND, value = 2),
        quantity = 1
    )
    val thirdItem = OrderItem(
        productId = UUID.randomUUID().toString(),
        amount = Amount( currency = USD, value = 20),
        quantity = 3
    )

    val items = mutableListOf( firstItem, secondItem)
    
    items.add(thirdItem)

    items.total(USD) shouldBe 
      ( firstItem.amount * firstItem.quantity + 
        thirdItem.amount * thirdItem.quantity )
  }
}
```

## Iflix

```kotlin
internal class OrderTest {

    @Test
    fun `order add an item`() {

        val theCustomer = Customer(
                            name = "Vu Hoang Viet",
                            email = "vietvuhoang@gmail.com")

        val orderId = UUID.randomUUID().toString()

        val shippingAddress = "DoiCan, BaDinh, Hanoi"

        val item1 = OrderItem( productId = UUID.randomUUID().toString(),
            amount = Amount( currency = USD, 10)
        )

        val item2 = OrderItem( productId = UUID.randomUUID().toString(),
            amount = Amount( currency = USD, 20)
        )

        // Create a Order
        var order = Order(  id = orderId,
                            customer = theCustomer,
                            address = shippingAddress,
                            items = listOf( item1 ))

        // Add one more Item to order
        order = order add item2

        order shouldBe  Order(  id = orderId,
            customer = theCustomer,
            address = shippingAddress,
            items = listOf( item1, item2 ))
    }
}
```