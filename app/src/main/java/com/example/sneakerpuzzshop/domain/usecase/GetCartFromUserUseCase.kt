package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.repository.CartRepository
import javax.inject.Inject

class GetCartFromUserUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userId: String): Resource<List<CartItemModel>> {
        if (userId.isBlank()) {
            Resource.Failure(Exception("User id không hợp lệ"))
        }
        return try {
            val cartItems = cartRepository.getCartFromUser(userId)
            Resource.Success(cartItems)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}