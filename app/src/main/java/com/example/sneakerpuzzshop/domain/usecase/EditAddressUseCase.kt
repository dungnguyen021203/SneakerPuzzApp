package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.repository.ProfileRepository
import javax.inject.Inject

class EditAddressUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, userAddress: String): Resource<Unit> {
        if (userAddress.isBlank())
            return Resource.Failure(Exception("Không được để trống field"))
        return profileRepository.editUserAddress(userId, userAddress)
    }
}