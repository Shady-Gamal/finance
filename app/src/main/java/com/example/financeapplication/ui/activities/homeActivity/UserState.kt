package com.example.financeapplication.ui.activities.homeActivity

import com.example.domain.entities.AppUserDTO

data class UserState(
    var isDataLoaded : AppUserDTO ?= null,
    var isAuthenticated : Boolean = true,
    var error: String ?= null,
    var isLoading : Boolean = false
)
