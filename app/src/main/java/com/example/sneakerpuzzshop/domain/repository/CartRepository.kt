package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.domain.model.CartItemModel

interface CartRepository {
    suspend fun getCartFromUser(userId: String): List<CartItemModel>
}