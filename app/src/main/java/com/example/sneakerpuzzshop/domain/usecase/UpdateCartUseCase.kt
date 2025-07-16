package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        userId: String,
        productId: String,
        size: String,
        quantity: Int
    ): Resource<Unit> {
        return try {
            cartRepository.updateCart(userId, productId, size, quantity)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}