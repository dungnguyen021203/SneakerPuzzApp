package com.example.sneakerpuzzshop.domain.model

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val cart: List<CartItemModel> = emptyList(),
    val phoneNumber: String = "",
    val address: String = "",
    val avatar: String = ""
)
