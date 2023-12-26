package com.example.domain.useCases


import com.example.domain.entities.TransactionDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.TransactionHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddTransactionDetailsUseCase @Inject constructor(val transactionHistoryRepository: TransactionHistoryRepository) {

    suspend operator fun invoke( transaction : TransactionDTO) : Flow<Resource<Boolean>> {

        return transactionHistoryRepository.addTransactionDetails(transaction)

    }
}