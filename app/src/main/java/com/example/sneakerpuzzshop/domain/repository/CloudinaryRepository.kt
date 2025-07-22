package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.domain.model.CloudinaryResponse
import com.example.sneakerpuzzshop.utils.others.CLOUDINARY_PRODUCT
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryRepository {
    @Multipart
    @POST("v1_1/$CLOUDINARY_PRODUCT/image/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody
    ): CloudinaryResponse
}