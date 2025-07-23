package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrderFromUserUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(userId: String, orderStatus: String): List<OrderModel> {
        return orderRepository.getOrderFromUser(userId, orderStatus)
    }
}