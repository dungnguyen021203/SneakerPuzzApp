package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.domain.model.ReviewModel

interface ReviewRepository {
    suspend fun getProductReviews(productId: String): List<ReviewModel>
}