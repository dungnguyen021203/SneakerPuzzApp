package com.example.sneakerpuzzshop.domain.model

import com.example.sneakerpuzzshop.utils.others.BillingResult
import com.google.firebase.Timestamp

data class OrderModel(
    val orderId: String = "",
    val userId: String = "",
    val userName: String? = "",
    val userPhoneNumber: String? = "",
    val userAddress: String? = "",
    val date: Timestamp = Timestamp.now(),
    val items: List<CartItemModel> = emptyList(),
    val status: String = "Pending",
    val billingResult: BillingResult = BillingResult()
)
