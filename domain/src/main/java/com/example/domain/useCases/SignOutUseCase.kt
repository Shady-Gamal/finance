package com.example.domain.useCases

import com.example.domain.models.Resource
import com.example.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return userRepository.signOut()
    }
}