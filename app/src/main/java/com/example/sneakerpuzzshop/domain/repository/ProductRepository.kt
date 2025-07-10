package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ProductModel

interface ProductRepository {
    suspend fun getProductsByCategory(categoryId: String): List<ProductModel>

    suspend fun getProductsDetails(productId: String): Resource<ProductModel>
}