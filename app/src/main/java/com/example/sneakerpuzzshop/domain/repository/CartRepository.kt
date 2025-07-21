package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.domain.model.CartItemModel

interface CartRepository {
    suspend fun getCartFromUser(userId: String): List<CartItemModel>

    suspend fun addToCart(userId: String, item: CartItemModel)

    suspend fun removeFromCart(userId: String, productId: String, size: String)

    suspend fun updateCart(userId: String, productId: String, size: String, quantity: Int)

    suspend fun clearCart(userId: String)
}