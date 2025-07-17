package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun signUp(email: String, name: String, password: String): Resource<FirebaseUser>

    suspend fun loginWithGoogle(idToken: String): Resource<FirebaseUser>

    fun logOut()

    suspend fun resetPassword(email: String): Resource<Unit>

    suspend fun getUserInformation(): Resource<UserModel>
}