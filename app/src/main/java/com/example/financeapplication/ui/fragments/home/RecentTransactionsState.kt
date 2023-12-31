package com.example.financeapplication.ui.fragments.home

import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.FinanceDTO
import com.example.domain.entities.TransactionDTO

data class RecentTransactionsState(
    var recentTransactions : List<TransactionDTO> ?= null,
    var isLoading : Boolean = false,
    var error: String ?= null
)
