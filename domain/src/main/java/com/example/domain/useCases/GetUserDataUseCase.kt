package com.example.domain.useCases

import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserDataUseCase(val userRepository: UserRepository) {

    suspend operator fun invoke(id : String ) : Flow<Resource<AppUserDTO>>{
        return userRepository.getUserData(id)

    }
}