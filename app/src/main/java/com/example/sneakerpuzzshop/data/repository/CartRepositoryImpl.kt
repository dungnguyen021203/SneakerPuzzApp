package com.example.sneakerpuzzshop.data.repository

import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.repository.CartRepository
import com.example.sneakerpuzzshop.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
): CartRepository {
    override suspend fun getCartFromUser(userId: String): List<CartItemModel> {
        val snapshot = firestore.collection("users")
            .document(userId)
            .get().await()
        val cart = snapshot.get("cart") as? List<*> ?: return emptyList()

        return cart.filterIsInstance<Map<String, Any>>().mapNotNull { map ->
            try {
                CartItemModel(
                    productId = map["productId"] as? String ?: "",
                    size = map["size"] as? Int ?: 0,
                    quantity = map["quantity"] as? Int ?: 1
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}