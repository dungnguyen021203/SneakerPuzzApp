package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.domain.usecase.AddToOrderUseCase
import com.example.sneakerpuzzshop.utils.others.BillingResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val addToOrderUseCase: AddToOrderUseCase
): ViewModel() {
    private val _order = MutableStateFlow<Resource<List<OrderModel>>>(Resource.Loading)
    val order: StateFlow<Resource<List<OrderModel>>> = _order

    fun addToOrder(
        userId: String,
        userName: String?,
        userPhoneNumber: String?,
        userAddress: String?,
        cartItem: List<CartItemModel>,
        billingResult: BillingResult
    ) {
        viewModelScope.launch {
            try {
                addToOrderUseCase(userId, userName, userPhoneNumber, userAddress, cartItem, billingResult)
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
    }
}