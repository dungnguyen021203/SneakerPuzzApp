package com.example.sneakerpuzzshop.domain.usecase

import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(uid: String): Resource<Unit> {
        return authRepository.deleteAccount(uid)
    }
}