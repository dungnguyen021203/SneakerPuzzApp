package com.example.sneakerpuzzshop.data.repository

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.repository.ProfileRepository
import com.example.sneakerpuzzshop.utils.others.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ProfileRepository {
    override suspend fun editUserName(userId: String, userName: String): Resource<Unit> {
        return try {
            firestore.collection("users").document(userId).update("name", userName).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun editUserPhone(
        userId: String,
        userPhone: String
    ): Resource<Unit> {
        return try {
            firestore.collection("users").document(userId).update("phoneNumber", userPhone).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun editUserAddress(
        userId: String,
        userAddress: String
    ): Resource<Unit> {
        return try {
            firestore.collection("users").document(userId).update("address", userAddress).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}