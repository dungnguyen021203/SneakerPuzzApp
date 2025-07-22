package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.repository.ProfileRepository
import javax.inject.Inject

class EditPhoneUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, userPhone: String): Resource<Unit> {
        if (userPhone.isBlank())
            return Resource.Failure(Exception("Không được để trống field"))
        return profileRepository.editUserPhone(userId, userPhone)
    }
}