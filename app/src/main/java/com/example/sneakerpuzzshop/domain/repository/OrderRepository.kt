package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.utils.others.BillingResult

interface OrderRepository {
    suspend fun addToOrder(
        userId: String,
        userName: String?,
        userPhoneNumber: String?,
        userAddress: String?,
        cartItem: List<CartItemModel>,
        billingResult: BillingResult
    )

    suspend fun getOrderFromUser(userId: String, orderStatus: String): List<OrderModel>

    suspend fun getOrderDetails(orderId: String): Resource<OrderModel>

    suspend fun cancelOrder(orderId: String): Resource<Unit>
}