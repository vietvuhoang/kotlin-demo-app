package vvu.study.kotlin.demo.dtos

import java.math.BigDecimal
import java.util.*

internal val USD : Currency = Currency.getInstance("USD")
internal val DEFAULT_CURRENCY = USD

data class Customer(
    val id: String = UUID.randomUUID().toString(),
    val name: String? = null,
    val email: String? = null
)

data class Amount(val currency: Currency, val value: BigDecimal) {
    constructor( currency: Currency, value: Long) : this(currency, BigDecimal.valueOf(value))
    constructor( currency: Currency, value: Int) : this(currency, value.toLong())
}

operator fun Amount.plus(amt: Amount): Amount {
    if (this.currency != amt.currency) throw IllegalArgumentException("Currency not match: ${this.currency} and ${amt.currency}")
    return this.copy(value = this.value + amt.value)
}

operator fun Amount.times(quantity: Int) = this.copy(value = this.value.multiply(BigDecimal.valueOf(quantity.toLong())))

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

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val customer: Customer,
    val address: String,
    val items: List<OrderItem> = emptyList()
)

infix fun Order.add(item: OrderItem): Order = this.copy(items = this.items + item)

fun Order.getCurrency(): Currency = this.items.firstOrNull()?.amount?.currency ?: DEFAULT_CURRENCY

fun Order.total(): Amount {
    val  currency = this.getCurrency()
    return this.items.total( currency )
}

class OrderBuilder(
    var id: String? = null,
    var customer: Customer? = null,
    var address: String? = null,
    var items: MutableList<OrderItem> = mutableListOf()
) : Builder<Order> {
    override fun build(): Order =
        Order(
            id = requireNotNull(this.id),
            customer = requireNotNull(this.customer),
            address = requireNotNull(this.address),
            items = items
        )
}
