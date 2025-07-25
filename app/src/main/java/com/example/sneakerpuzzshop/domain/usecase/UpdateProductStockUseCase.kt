package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.repository.OrderRepository
import javax.inject.Inject

class UpdateProductStockUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(cartItems: List<CartItemModel>) {
        return orderRepository.updateProductStock(cartItems)
    }
}