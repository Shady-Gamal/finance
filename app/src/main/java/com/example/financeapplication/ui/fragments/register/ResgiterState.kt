package com.example.financeapplication.ui.fragments.register

data class RegisterState(
    var isRegistered : Boolean = false,
    var isLoading : Boolean = false,
    var error: String ?= null
)
