package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userId: String) {
        return cartRepository.clearCart(userId)
    }
}