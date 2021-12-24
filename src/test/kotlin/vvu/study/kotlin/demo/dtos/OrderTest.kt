package vvu.study.kotlin.demo.dtos

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

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