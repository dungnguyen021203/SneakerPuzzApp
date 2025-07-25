package com.example.sneakerpuzzshop.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.domain.usecase.GetEveryProductUseCase
import com.example.sneakerpuzzshop.domain.usecase.ProductDetailsUseCase
import com.example.sneakerpuzzshop.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val productDetailsUseCase: ProductDetailsUseCase,
    private val getEveryProductUseCase: GetEveryProductUseCase
) : ViewModel() {
    private val _product = MutableStateFlow<Resource<List<ProductModel>>>(Resource.Loading)
    val product: StateFlow<Resource<List<ProductModel>>> = _product

    private val _productDetails = MutableStateFlow<Resource<ProductModel>>(Resource.Loading)
    val productDetails: StateFlow<Resource<ProductModel>> = _productDetails

    private val _everyProduct = MutableStateFlow<Resource<List<ProductModel>>>(Resource.Loading)
    val everyProduct: StateFlow<Resource<List<ProductModel>>> = _everyProduct

    private val _query = MutableStateFlow<String>("")
    val query: StateFlow<String> = _query

    private val _searchResults = MutableStateFlow<List<ProductModel>>(emptyList())
    val searchResult: StateFlow<List<ProductModel>> = _searchResults

    init {
        getEveryProduct()
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val suggestions: StateFlow<List<String>> =
        query.debounce(300).mapLatest { q ->
            if (q.isBlank()) {
                emptyList()
            } else {
                val productList = (_everyProduct.value as? Resource.Success)?.data ?: emptyList()
                productList.filter {
                    it.name.contains(q, true) || it.category.contains(q, true)
                }.map { it.name }
                    .distinct().take(5)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                _product.value = Resource.Loading
                val result = productUseCase(categoryId)
                _product.value = Resource.Success(result)
            } catch (e: Exception) {
                _product.value = Resource.Failure(e)
            }
        }
    }

    fun loadProductDetails(productId: String) {
        viewModelScope.launch {
            _productDetails.value = Resource.Loading
            val result = productDetailsUseCase(productId)
            _productDetails.value = result
        }
    }

    fun getEveryProduct() {
        viewModelScope.launch {
            getEveryProductUseCase()
                .onStart { _everyProduct.value = Resource.Loading }
                .catch { e -> _everyProduct.value = Resource.Failure(e as Exception) }
                .collect { list ->
                    _everyProduct.value = Resource.Success(list)
                }
        }
    }

    fun onQueryChange(q: String) {
        _query.value = q
    }

    fun performSearch(query: String) {
        viewModelScope.launch {
            when(val result = _everyProduct.value) {
                is Resource.Failure -> {
                    _everyProduct.value = Resource.Failure(Exception("Cant search"))
                }
                Resource.Loading -> {
                }
                is Resource.Success -> {
                    val productList = result.data
                    _searchResults.value =  productList.filter {
                        it.name.contains(query, true) || it.category.contains(query, true)
                    }
                }
            }
         }
    }
}