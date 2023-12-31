package com.example.domain.useCases

import com.example.domain.entities.RecipientDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.RecipientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchRecipientsUseCase @Inject constructor(val recipientRepository: RecipientRepository) {

    suspend operator fun invoke () : Flow<Resource<MutableList<RecipientDTO>?>>{

        return recipientRepository.getRecipients()
    }
}