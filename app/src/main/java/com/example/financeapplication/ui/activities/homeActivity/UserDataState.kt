package com.example.financeapplication.ui.activities.homeActivity

import com.example.domain.entities.AppUserDTO

data class UserDataState(
    var isLoaded : AppUserDTO ?= null,
    var doesntexist : Boolean = false,
    var error: String ?= null,
    var isLoading : Boolean = false
)
