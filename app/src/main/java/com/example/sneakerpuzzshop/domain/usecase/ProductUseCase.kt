package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.domain.repository.ProductRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(categoryId: String): List<ProductModel> {
        return productRepository.getProductsByCategory(categoryId)
    }
}