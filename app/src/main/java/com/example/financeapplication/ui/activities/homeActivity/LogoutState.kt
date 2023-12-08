package com.example.financeapplication.ui.activities.homeActivity

data class LogoutState(

    var isLoggedOut : Boolean = false,
    var isLoading : Boolean = false,
    var error: String ?= null

)
