package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEveryProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke() : Flow<List<ProductModel>> {
        return productRepository.getEveryProduct()
    }
}