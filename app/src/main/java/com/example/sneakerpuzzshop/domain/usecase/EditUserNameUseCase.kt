package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.repository.ProfileRepository
import com.example.sneakerpuzzshop.utils.others.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class EditUserNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, userName: String): Resource<Unit> {
        if (userName.isBlank())
            return Resource.Failure(Exception("Không được để trống field"))
        return profileRepository.editUserName(userId, userName)
    }
}