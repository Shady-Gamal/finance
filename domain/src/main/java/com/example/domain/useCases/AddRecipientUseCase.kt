package com.example.domain.useCases

import com.example.domain.entities.RecipientDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.RecipientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRecipientUseCase @Inject constructor(val recipientRepository: RecipientRepository) {

    suspend operator fun invoke(recipientId: String) : Flow<Resource<Boolean>>{

        return recipientRepository.addRecipient(recipientId)
    }
}