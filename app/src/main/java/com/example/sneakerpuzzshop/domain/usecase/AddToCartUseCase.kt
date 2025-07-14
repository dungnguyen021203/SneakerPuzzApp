package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userId: String, item: CartItemModel): Resource<Unit> {
        return try {
            cartRepository.addToCart(userId, item)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}