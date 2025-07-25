package com.example.sneakerpuzzshop.data.repository

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.domain.repository.OrderRepository
import com.example.sneakerpuzzshop.utils.others.BillingResult
import com.example.sneakerpuzzshop.utils.others.ORDER_STATUS_LIST
import com.example.sneakerpuzzshop.utils.others.await
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrderRepository {
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

    override suspend fun getOrderFromUser(userId: String, orderStatus: String): List<OrderModel> {
        val result = firestore.collection("orders")
            .whereEqualTo("userId", userId)
            .whereEqualTo("status", orderStatus)
            .get().await()
        return result.documents.mapNotNull { doc ->
            doc.toObject(OrderModel::class.java)
        }
    }

    override suspend fun getOrderDetails(
        orderId: String
    ): Resource<OrderModel> {
        return try {
            val snapshot = firestore.collection("orders")
                .document(orderId)
                .get().await()
            val result = snapshot.toObject(OrderModel::class.java)

            if (result == null) {
                Resource.Failure(Exception("Không tìm thấy order"))
            } else {

                Resource.Success(result)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun cancelOrder(orderId: String): Resource<Unit> {
        return try {
            val db = firestore.collection("orders").document(orderId)
            val orderRef = firestore.collection("orders").document(orderId).get().await()

            if (orderRef.exists()) {
                val currentStatus = orderRef.getString("status")
                when(currentStatus) {
                    ORDER_STATUS_LIST[0], ORDER_STATUS_LIST[2] -> {
                        db.update("status", ORDER_STATUS_LIST[3])
                            .await()
                        Resource.Success(Unit)
                    }
                    ORDER_STATUS_LIST[1] -> {
                        Resource.Failure(Exception("Order đã được shipped, không thể cancel"))
                    }
                    else -> {
                        Resource.Failure(Exception("Không thể cancel order"))
                    }
                }
            } else {
                Resource.Failure(Exception("Order not found"))
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateProductStock(cartItems: List<CartItemModel>){
        return try {
            // race condition
            cartItems.forEach { cart ->
                val ref = firestore.collection("data").document("stocks")
                    .collection("product").document(cart.productId)
                firestore.runTransaction { transaction ->
                    val snapshot = transaction.get(ref)
                    val sizesMap = snapshot.get("sizes") as? Map<String, Long> ?: emptyMap()
                    val currentStock = sizesMap[cart.size] ?: 0L
                    val newStock = (currentStock - cart.quantity).coerceAtLeast(0)

                    val updatedMap = sizesMap.toMutableMap()
                    updatedMap[cart.size] = newStock

                    transaction.update(ref, "sizes", updatedMap)
                }.await()
            }
        } catch (e: CancellationException) {
                throw e
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}