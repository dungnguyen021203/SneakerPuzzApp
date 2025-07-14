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
                    size = (map["size"] as? Long)?.toInt() ?: 0,
                    quantity = (map["quantity"] as? Long)?.toInt() ?: 1
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // Add to cart, if alr + 1
    override suspend fun addToCart(
        userId: String,
        item: CartItemModel
    ) {
        val currentCart = getCartFromUser(userId).toMutableList()
        val index = currentCart.indexOfFirst { it.productId == item.productId && it.size == item.size }

        if (index >= 0) {
            currentCart[index] = currentCart[index].copy(
                quantity = currentCart[index].quantity + item.quantity
            )
        } else {
            currentCart.add(item)
        }

        firestore.collection("users").document(userId).update("cart", currentCart).await()
    }

    override suspend fun removeFromCart(
        userId: String,
        productId: String,
        size: Int
    ) {
        val currentCart = getCartFromUser(userId).filterNot { it.productId == productId && it.size == size }
        firestore.collection("users").document(userId).update("cart", currentCart).await()
    }

    override suspend fun updateCart(
        userId: String,
        productId: String,
        size: Int,
        quantity: Int
    ) {
        val currentCart = getCartFromUser(userId).map {
            if (it.productId == productId && it.size == size) {
                it.copy(quantity = quantity)
            } else {
                it
            }
        }
        firestore.collection("users").document(userId).update("cart", currentCart).await()
    }
}