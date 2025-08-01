package com.example.sneakerpuzzshop.data.repository

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.domain.repository.ProductRepository
import com.example.sneakerpuzzshop.utils.others.await
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override suspend fun getProductsDetails(productId: String): Resource<ProductModel> {
        return try {
            val snapshot = firestore.collection("data")
                .document("stocks")
                .collection("product")
                .document(productId).get().await()
            val result = snapshot.toObject(ProductModel::class.java)
            if (result == null) {
                Resource.Failure(Exception("Không tìm thấy sản phẩm"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun getEveryProduct(): Flow<List<ProductModel>> = callbackFlow {
        val listenerRegistration = firestore
            .collection("data")
            .document("stocks")
            .collection("product")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // đóng Flow nếu có lỗi
                    return@addSnapshotListener
                }

                val products = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ProductModel::class.java)
                } ?: emptyList()

                trySend(products) // emit dữ liệu mới
            }

        awaitClose { listenerRegistration.remove() }
    }
}