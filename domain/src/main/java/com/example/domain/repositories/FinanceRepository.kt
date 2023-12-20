package com.example.domain.repositories

import com.example.domain.entities.FinanceDTO
import com.example.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface FinanceRepository{

     suspend fun getFinanceDetails (id : String) : Flow<Resource<FinanceDTO>>

     suspend fun transferFunds(id : String, value : Double) : Flow<Resource<Boolean>>

 }