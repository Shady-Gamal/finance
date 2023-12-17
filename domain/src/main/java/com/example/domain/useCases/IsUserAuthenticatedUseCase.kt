package com.example.domain.useCases

import com.example.domain.repositories.UserRepository
import javax.inject.Inject

class IsUserAuthenticatedUseCase@Inject constructor(val userRepository: UserRepository) {

    operator fun invoke(): String? {
        return userRepository.isUserAuthenticatedInFirebase()
    }
}