package com.example.domain.useCases

import com.example.domain.models.Resource
import com.example.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class SignOutUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return userRepository.signOut()
    }
}