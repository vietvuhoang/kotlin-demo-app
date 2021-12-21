# Unit Testing

```kotlin
internal class AmountTest {
    private companion object {
        val VND : Currency = Currency.getInstance("VND")
    }

    @Test
    fun `plus amount`() {
        val amount10usd = Amount( currency = USD, value = 10)
        val amount15usd = Amount( currency = USD, value = 15)
        // push operator
        println(amount10usd + amount15usd)
        // time operator
        println( amount10usd * 3)

        amount10usd + amount15usd shouldBe
            Amount( currency = USD, value = (amount10usd.value + amount15usd.value))
    }

    @Test
    fun `multiple amount`() {

        val amount10usd = Amount( currency = USD, value = 10)
        val times = 3
        amount10usd * times shouldBe 
            Amount( currency = USD, 
                    value = (amount10usd.value.multiply(BigDecimal.valueOf(times.toLong()))))
    }

    @Test
    fun `plus amount with difference currencies`() {
        val amount10usd = Amount( currency = USD, value = 10)
        val amount15000vnd = Amount( currency = VND, value = 15000)
        shouldThrowExactly<IllegalArgumentException> {  amount10usd + amount15000vnd }
    }
}
```
