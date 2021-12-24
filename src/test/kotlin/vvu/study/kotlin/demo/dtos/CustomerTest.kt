package vvu.study.kotlin.demo.dtos

import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import java.util.*

internal class CustomerTest {

    @Test
    fun testCopy() {
        val tmpCustomer = Customer( id = UUID.randomUUID().toString())
        // Update Customer information
        val customer = tmpCustomer.copy( name = "Vu Hoang Viet", email = "vietvuhoang@gmail.com")
        println(customer)
        customer shouldNotBeSameInstanceAs tmpCustomer
    }

}