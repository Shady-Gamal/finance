package com.example.domain.repositories

import com.example.domain.entities.TransactionDTO
import com.example.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface TransactionHistoryRepository {

    suspend fun addTransactionDetails(transaction : TransactionDTO) : Flow<Resource<Boolean>>

    suspend fun getTransactionHistory(id : String) : Flow<Resource<List<TransactionDTO>>>
}