package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.usecase.GetCartFromUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartFromUserUseCase: GetCartFromUserUseCase
): ViewModel() {

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
}