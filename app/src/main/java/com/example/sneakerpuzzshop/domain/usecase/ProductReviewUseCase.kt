package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.domain.model.ReviewModel
import com.example.sneakerpuzzshop.domain.repository.ReviewRepository
import javax.inject.Inject

class ProductReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(productId: String): List<ReviewModel> {
        return reviewRepository.getProductReviews(productId)
    }
}