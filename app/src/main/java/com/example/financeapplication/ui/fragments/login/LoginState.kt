package com.example.financeapplication.ui.fragments.login

import com.example.domain.entities.AppUserDTO

data class LoginState(
    var isLoggedIn : Boolean = false,
    var isLoading : Boolean = false,
    var error: String ?= null
)
