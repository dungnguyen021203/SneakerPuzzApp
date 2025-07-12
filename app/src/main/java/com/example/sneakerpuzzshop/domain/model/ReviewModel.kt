package com.example.sneakerpuzzshop.domain.model

import com.google.firebase.Timestamp

data class ReviewModel(
    val id: String = "",
    val name: String = "",
    val avatar: String = "",
    val content: String = "",
    val response: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val rating: Double = 0.0
)