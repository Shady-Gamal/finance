package com.example.financeapplication.ui.fragments.home

import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.FinanceDTO

data class FinanceDetailsState(
    var financeDetails : FinanceDTO ?= null,
    var isLoading : Boolean = false,
    var error: String ?= null
)
