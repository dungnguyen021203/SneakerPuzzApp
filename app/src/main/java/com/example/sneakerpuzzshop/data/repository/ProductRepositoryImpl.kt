package com.example.sneakerpuzzshop.data.repository

import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.domain.repository.ProductRepository
import com.example.sneakerpuzzshop.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ProductRepository {
    override suspend fun getProductsByCategory(categoryId: String): List<ProductModel> {
        val result = firestore.collection("data")
            .document("stocks")
            .collection("product")
            .whereEqualTo("category", categoryId)
            .get().await()
        return result.documents.mapNotNull { doc ->
            doc.toObject(ProductModel::class.java)
        }
    }
}