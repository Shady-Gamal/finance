package com.example.domain.useCases

import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(val userRepository: UserRepository) {

    suspend operator fun invoke(email : String, password:String): Flow<Resource<Boolean>> {
            return userRepository.signInWithEmailAndPassword(email, password)
    }

}