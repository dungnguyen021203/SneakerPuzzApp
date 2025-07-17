package com.example.sneakerpuzzshop.data.repository

import com.example.sneakerpuzzshop.domain.model.ReviewModel
import com.example.sneakerpuzzshop.domain.repository.ReviewRepository
import com.example.sneakerpuzzshop.utils.others.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ReviewRepository {
    override suspend fun getProductReviews(productId: String): List<ReviewModel> {
        val result = firestore.collection("data")
            .document("stocks").collection("reviews")
            .whereEqualTo("productId", productId)
            .get().await()
        return result.documents.mapNotNull { doc ->
            doc.toObject(ReviewModel::class.java)
        }
    }
}