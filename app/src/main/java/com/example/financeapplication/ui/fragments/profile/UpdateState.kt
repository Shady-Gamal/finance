package com.example.financeapplication.ui.fragments.profile

data class UpdateState(
    var isUpdated : Boolean = false,
    var isLoading : Boolean = false,
    var error: String ?= null
)
