package com.example.sneakerpuzzshop.utils.others

import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.ProductModel

data class BillingResult(
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double = 0.0
)

object BillingHelper {
    private const val TAX_RATE = 0.10
    private const val SHIPPING_FEE = 20_000.0

    fun calculate(
        cartItems: List<CartItemModel>,
        productMap: Map<String, ProductModel>,
        shippingFeeOverride: Double? = null
    ): BillingResult {
        val subtotal = cartItems.sumOf { cart ->
            val product = productMap[cart.productId]
            product?.actualPrice?.toDouble()?.times(cart.quantity) ?: 0.0
        }
        val tax = subtotal * TAX_RATE
        val shippingFee = shippingFeeOverride ?: SHIPPING_FEE
        val total = subtotal + tax + shippingFee

        return BillingResult(
            subtotal = subtotal,
            tax = tax,
            shipping = shippingFee,
            total = total
        )
    }
}