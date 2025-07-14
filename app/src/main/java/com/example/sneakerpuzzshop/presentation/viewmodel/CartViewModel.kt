package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.usecase.AddToCartUseCase
import com.example.sneakerpuzzshop.domain.usecase.GetCartFromUserUseCase
import com.example.sneakerpuzzshop.domain.usecase.RemoveFromCartUseCase
import com.example.sneakerpuzzshop.domain.usecase.UpdateCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartFromUserUseCase: GetCartFromUserUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase
) : ViewModel() {

    private val _cart = MutableStateFlow<Resource<List<CartItemModel>>>(Resource.Loading)
    val cart: StateFlow<Resource<List<CartItemModel>>> = _cart

    fun getCartFromUser(userId: String) {
        viewModelScope.launch {
            try {
                _cart.value = Resource.Loading
                val result = getCartFromUserUseCase(userId)
                _cart.value = result
            } catch (e: Exception) {
                _cart.value = Resource.Failure(e)
            }
        }
    }

    fun addToCart(userId: String, item: CartItemModel) {
        viewModelScope.launch {
            val result = addToCartUseCase(userId, item)
            when (result) {
                is Resource.Failure -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success<*> -> getCartFromUser(userId)
            }
        }
    }

    fun removeFromCart(
        userId: String,
        productId: String,
        size: Int
    ) {
        viewModelScope.launch {
            val result = removeFromCartUseCase(userId, productId, size)
            when (result) {
                is Resource.Failure -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success<*> -> getCartFromUser(userId)
            }
        }
    }

    fun updateCart(
        userId: String,
        productId: String,
        size: Int,
        quantity: Int
    ) {
        viewModelScope.launch {
            val result = updateCartUseCase(userId, productId, size, quantity)
            when (result) {
                is Resource.Failure -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success<*> -> getCartFromUser(userId)
            }
        }
    }
}