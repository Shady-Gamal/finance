package com.example.domain.useCases

import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UpdateUserDataUseCase(val userRepository: UserRepository){

    suspend operator fun invoke(user : AppUserDTO): Flow<Resource<Boolean>>
    {
        return userRepository.updateUserData(user)

    }
}