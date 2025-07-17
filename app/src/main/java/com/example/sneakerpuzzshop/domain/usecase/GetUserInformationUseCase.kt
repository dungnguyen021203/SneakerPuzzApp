package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.UserModel
import com.example.sneakerpuzzshop.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Resource<UserModel> {
        return authRepository.getUserInformation()
    }
}