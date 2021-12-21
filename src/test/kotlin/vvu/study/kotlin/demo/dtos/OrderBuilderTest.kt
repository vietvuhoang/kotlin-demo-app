package vvu.study.kotlin.demo.dtos

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.util.*

internal class OrderBuilderTest {

    @Test
    fun `build an order`() {

        val theCustomer = Customer( name = "Vu Hoang Viet", email = "vietvuhoang@gmail.com")
        val orderId = UUID.randomUUID().toString()
        val shippingAddress = "DoiCan, BaDinh, Hanoi"
        val toy = OrderItem( productId = UUID.randomUUID().toString(),
                            amount = Amount( currency = USD, 10)
        )

        val toy2 = OrderItem( productId = UUID.randomUUID().toString(),
            amount = Amount( currency = USD, 20)
        )

        val order = OrderBuilder().apply {
            id = orderId
            customer = theCustomer
            address = shippingAddress
            items.apply {
                add(toy)
                add(toy2)
            }
        }.build()

        val checkingOrder = Order(id = orderId,
                customer = theCustomer,
                address = shippingAddress, items = listOf( toy, toy2))

        order shouldBe checkingOrder
    }
}