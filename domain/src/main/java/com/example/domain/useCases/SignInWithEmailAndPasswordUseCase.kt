package com.example.domain.useCases

import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(val authRepository: AuthRepository) {

    suspend operator fun invoke(email : String, password:String): Flow<Resource<AppUserDTO?>> {
            return authRepository.signInWithEmailAndPassword(email, password)
    }

}