package com.example.domain.useCases

import com.example.domain.models.Resource
import com.example.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignOutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return authRepository.signOut()
    }
}