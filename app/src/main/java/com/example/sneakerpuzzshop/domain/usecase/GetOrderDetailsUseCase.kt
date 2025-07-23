package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(orderId: String): Resource<OrderModel> {
        return orderRepository.getOrderDetails(orderId)
    }
}