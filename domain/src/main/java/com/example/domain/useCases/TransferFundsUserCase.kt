package com.example.domain.useCases

import com.example.domain.models.Resource
import com.example.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransferFundsUserCase @Inject constructor( val financeRepository: FinanceRepository) {

    suspend operator fun invoke(id : String, value : Double) : Flow<Resource<Boolean>>{

        return financeRepository.transferFunds(id, value)
    }
}