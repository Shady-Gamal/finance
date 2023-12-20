package com.example.domain.useCases

import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpWithEmailAndPasswordUseCase @Inject constructor(val userRepository: UserRepository) {

    suspend operator fun invoke(email : String, password : String , user: AppUserDTO): Flow<Resource<Boolean>> {
        return userRepository.singUpWithEmailAndPassword(email, password, user)
    }
}