package com.example.sneakerpuzzshop.domain.model

data class ProductModel(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val price: String = "",
    val actualPrice: String = "",
    val rating: Double = 0.0,
    val stockStatus: String = "",
    val sizes: Map<String, Int> = mapOf()
)
