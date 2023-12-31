package com.example.financeapplication.ui.fragments.history

import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.FinanceDTO
import com.example.domain.entities.TransactionDTO

data class HistoryState(
    var history : List<TransactionDTO> ?= null,
    var isLoading : Boolean = false,
    var error: String ?= null
)
