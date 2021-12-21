package vvu.study.kotlin.demo.dtos

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

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
            amount = Amount( currency = VND, value = 20000),
            quantity = 1
        )
        val thirdItem = OrderItem(
            productId = UUID.randomUUID().toString(),
            amount = Amount( currency = USD, value = 20),
            quantity = 3
        )
        val items = mutableListOf( firstItem, secondItem)
        items.add(thirdItem)
        items.total(USD) shouldBe ( firstItem.amount * firstItem.quantity + thirdItem.amount * thirdItem.quantity )
    }
}