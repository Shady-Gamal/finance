package com.example.domain.repositories

import com.example.domain.entities.RecipientDTO
import com.example.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface RecipientRepository {


    suspend fun addRecipient(recipient : String) : Flow<Resource<Boolean>>

    suspend fun getRecipients() : Flow<Resource<MutableList<RecipientDTO>?>>
}