package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.domain.repository.FavoriteRepository
import com.example.sneakerpuzzshop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    productRepository: ProductRepository
) : ViewModel() {
    val favorites = favoriteRepository.favorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptySet())

    val favoriteProducts =
        combine(favorites, productRepository.getEveryProduct()) { ids, allProducts ->
            allProducts.filter { it.id in ids }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(productId)
        }
    }
}