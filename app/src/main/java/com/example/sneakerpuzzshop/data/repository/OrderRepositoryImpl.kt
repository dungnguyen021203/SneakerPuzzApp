package com.example.sneakerpuzzshop.data.repository

import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.domain.repository.OrderRepository
import com.example.sneakerpuzzshop.utils.others.BillingResult
import com.example.sneakerpuzzshop.utils.others.await
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): OrderRepository {
    override suspend fun addToOrder(
        userId: String,
        userName: String?,
        userPhoneNumber: String?,
        userAddress: String?,
        cartItem: List<CartItemModel>,
        billingResult: BillingResult
    ) {
        val orderSnapshot = OrderModel(
            orderId = "ORD" + UUID.randomUUID().toString().replace("-", "").take(10).uppercase(),
            userId = userId,
            userName = userName,
            userAddress = userAddress,
            userPhoneNumber = userPhoneNumber,
            date = Timestamp.now(),
            items = cartItem,
            status = "PENDING", // TODO: After specific of time -> ...
            billingResult = billingResult
        )
        firestore.collection("orders").document(orderSnapshot.orderId).set(orderSnapshot).await()
    }
}