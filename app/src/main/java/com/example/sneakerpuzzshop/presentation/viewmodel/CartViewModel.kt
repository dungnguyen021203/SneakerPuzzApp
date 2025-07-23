package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.domain.usecase.AddToCartUseCase
import com.example.sneakerpuzzshop.domain.usecase.ClearCartUseCase
import com.example.sneakerpuzzshop.domain.usecase.GetCartFromUserUseCase
import com.example.sneakerpuzzshop.domain.usecase.ProductDetailsUseCase
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
    private val updateCartUseCase: UpdateCartUseCase,
    private val productDetailsUseCase: ProductDetailsUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _cart = MutableStateFlow<Resource<List<CartItemModel>>>(Resource.Loading)
    val cart: StateFlow<Resource<List<CartItemModel>>> = _cart

    private val _productDetailsMap = MutableStateFlow<Map<String, ProductModel>>(emptyMap())
    val productDetailsMap: StateFlow<Map<String, ProductModel>> = _productDetailsMap

    fun getCartFromUser(userId: String) {
        viewModelScope.launch {
            try {
                _cart.value = Resource.Loading
                val cartItem = getCartFromUserUseCase(userId)
                _cart.value = cartItem

                if (cartItem is Resource.Success) {
                    val productMap = mutableMapOf<String, ProductModel>()
                    for (item in cartItem.data) {
                        val result = productDetailsUseCase(item.productId)
                        if (result is Resource.Success) {
                            productMap[item.productId] = result.data
                        }
                    }
                    _productDetailsMap.value = productMap
                }
            } catch (e: Exception) {
                _cart.value = Resource.Failure(e)
            }
        }
    }

    fun addToCart(userId: String, item: CartItemModel) {
        viewModelScope.launch {
            try {
                val result = addToCartUseCase(userId, item)
                if (result is Resource.Success) {
                    val currentCart = (_cart.value as? Resource.Success)?.data?.toMutableList() ?: return@launch
                    val index = currentCart.indexOfFirst { it.productId == item.productId && it.size == item.size }
                    if (index >= 0) {
                        currentCart[index] = currentCart[index].copy(quantity = currentCart[index].quantity + item.quantity)
                    } else {
                        currentCart.add(item)
                    }
                    _cart.value = Resource.Success(currentCart)
                }
            } catch (e: Exception) {
                _cart.value = Resource.Failure(e)
            }
        }
    }

    fun removeFromCart(
        userId: String,
        productId: String,
        size: String
    ) {
        viewModelScope.launch {
            try {
                val result = removeFromCartUseCase(userId, productId, size)
                if (result is Resource.Success) {
                    val currentCart = (_cart.value as? Resource.Success)?.data?.toMutableList() ?: return@launch
                    val updated = currentCart.filterNot { it.productId == productId && it.size == size }
                    _cart.value = Resource.Success(updated)
                }
            } catch (e: Exception) {
                _cart.value = Resource.Failure(e)
            }

        }
    }

    fun updateCart(userId: String, productId: String, size: String, quantity: Int) {
        viewModelScope.launch {
            try {
                if (quantity <= 0) {
                    removeFromCart(userId, productId, size)
                    return@launch
                }

                val result = updateCartUseCase(userId, productId, size, quantity)
                if (result is Resource.Success) {
                    val currentCart = (_cart.value as? Resource.Success)?.data?.toMutableList() ?: return@launch
                    val index = currentCart.indexOfFirst { it.productId == productId && it.size == size }
                    if (index >= 0) {
                        currentCart[index] = currentCart[index].copy(quantity = quantity)
                        _cart.value = Resource.Success(currentCart)
                    }
                }
            } catch (e: Exception) {
                _cart.value = Resource.Failure(e)
            }
        }
    }

    fun clearCart(userId: String) {
        viewModelScope.launch {
            try {
                clearCartUseCase(userId)
                _cart.value = Resource.Success(emptyList())
            } catch (e: Exception) {
                _cart.value = Resource.Failure(e)
            }
        }
    }

    fun getProductFromOrder(orderItems: List<CartItemModel>) {
        viewModelScope.launch {
            val productMap = mutableMapOf<String, ProductModel>()
            for (item in orderItems) {
                val result = productDetailsUseCase(item.productId)
                if (result is Resource.Success) {
                    productMap[item.productId] = result.data
                }
            }
            _productDetailsMap.value = productMap
        }
    }


}