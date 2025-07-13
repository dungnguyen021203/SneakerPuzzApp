package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ReviewModel
import com.example.sneakerpuzzshop.domain.usecase.ProductReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val productReviewUseCase: ProductReviewUseCase
): ViewModel() {

    private val _review = MutableStateFlow<Resource<List<ReviewModel>>>(Resource.Loading)
    val review: StateFlow<Resource<List<ReviewModel>>> = _review

    fun loadProductReviews(productId: String) {
        viewModelScope.launch {
            try {
                _review.value = Resource.Loading
                val result = productReviewUseCase(productId)
                _review.value = Resource.Success(result)
            } catch (e: Exception) {
                _review.value = Resource.Failure(e)
            }
        }
    }
}