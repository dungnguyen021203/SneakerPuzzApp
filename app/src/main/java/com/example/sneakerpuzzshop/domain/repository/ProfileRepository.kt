package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.common.Resource

interface ProfileRepository {
    suspend fun editUserName(userId: String, userName: String): Resource<Unit>

    suspend fun editUserPhone(userId: String, userPhone: String): Resource<Unit>

    suspend fun editUserAddress(userId: String, userAddress: String): Resource<Unit>
}