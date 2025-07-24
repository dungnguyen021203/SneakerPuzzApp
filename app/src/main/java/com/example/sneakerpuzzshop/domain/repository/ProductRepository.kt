package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductsByCategory(categoryId: String): List<ProductModel>

    suspend fun getProductsDetails(productId: String): Resource<ProductModel>

    fun getEveryProduct(): Flow<List<ProductModel>>
}