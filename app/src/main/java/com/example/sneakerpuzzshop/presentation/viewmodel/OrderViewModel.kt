package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.domain.usecase.AddToOrderUseCase
import com.example.sneakerpuzzshop.domain.usecase.CancelOrderUseCase
import com.example.sneakerpuzzshop.domain.usecase.GetOrderDetailsUseCase
import com.example.sneakerpuzzshop.domain.usecase.GetOrderFromUserUseCase
import com.example.sneakerpuzzshop.utils.others.BillingResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val addToOrderUseCase: AddToOrderUseCase,
    private val getOrderFromUserUseCase: GetOrderFromUserUseCase,
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase
): ViewModel() {
    private val _order = MutableStateFlow<Resource<List<OrderModel>>>(Resource.Loading)
    val order: StateFlow<Resource<List<OrderModel>>> = _order

    private val _orderDetails = MutableStateFlow<Resource<OrderModel>>(Resource.Loading)
    val orderDetails: StateFlow<Resource<OrderModel>> = _orderDetails

    private val _cancelOrder = MutableStateFlow<Resource<Unit>?>(null)
    val cancelOrder: StateFlow<Resource<Unit>?> = _cancelOrder

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

    fun getOrder(userId: String, orderStatus: String) = viewModelScope.launch {
        try {
            _order.value = Resource.Loading
            val result = getOrderFromUserUseCase(userId, orderStatus)
            _order.value = Resource.Success(result)
        } catch (e: Exception) {
            _order.value = Resource.Failure(e)
        }
    }

    fun getOrderDetails(orderId: String) = viewModelScope.launch {
        try {
            _orderDetails.value = Resource.Loading
            val result = getOrderDetailsUseCase(orderId)
            _orderDetails.value = result
        } catch (e: Exception) {
            _orderDetails.value = Resource.Failure(e)
        }
    }

    fun cancelOrder(orderId: String) = viewModelScope.launch {
        try {
            _cancelOrder.value = Resource.Loading
            val result = cancelOrderUseCase(orderId)
            _cancelOrder.value = result
        } catch (e: Exception) {
            _cancelOrder.value = Resource.Failure(e)
        }
    }
}