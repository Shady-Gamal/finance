package com.example.domain.useCases

import com.example.domain.entities.TransactionDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.TransactionHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetRecentTransactionsUseCase @Inject constructor(val transactionHistoryRepository: TransactionHistoryRepository) {

    suspend operator fun invoke(id : String) : Flow<Resource<List<TransactionDTO>>> {

       return transactionHistoryRepository.getRecentTransactions(id)

    }
}