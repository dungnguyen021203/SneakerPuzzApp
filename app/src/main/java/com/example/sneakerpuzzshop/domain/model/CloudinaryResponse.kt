package com.example.sneakerpuzzshop.domain.model

import com.google.gson.annotations.SerializedName

data class CloudinaryResponse(
    @SerializedName("secure_url")
    val secureUrl: String?
)
