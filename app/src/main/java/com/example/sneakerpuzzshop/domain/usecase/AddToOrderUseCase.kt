package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.repository.OrderRepository
import com.example.sneakerpuzzshop.utils.others.BillingResult
import javax.inject.Inject

class AddToOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(
        userId: String,
        userName: String?,
        userPhoneNumber: String?,
        userAddress: String?,
        cartItem: List<CartItemModel>,
        billingResult: BillingResult
    ): Resource<Unit> {
        return try {
            orderRepository.addToOrder(
                userId,
                userName,
                userPhoneNumber,
                userAddress,
                cartItem,
                billingResult
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}