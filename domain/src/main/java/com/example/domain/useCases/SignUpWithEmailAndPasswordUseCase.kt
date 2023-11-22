package com.example.domain.useCases

import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignUpWithEmailAndPasswordUseCase(val authRepository: AuthRepository) {

    suspend operator fun invoke(email : String, password : String , user: AppUserDTO): Flow<Resource<Boolean>> {
        return authRepository.singUpWithEmailAndPassword(email, password, user)
    }
}