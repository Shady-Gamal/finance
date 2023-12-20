package com.example.domain.useCases

import com.example.domain.entities.FinanceDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFinanceDetailsUseCase @Inject constructor( val financeRepository: FinanceRepository) {

    suspend operator fun invoke(id : String) : Flow<Resource<FinanceDTO>>{
        return financeRepository.getFinanceDetails(id)
    }
}
