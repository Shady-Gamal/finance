package com.example.financeapplication.ui.fragments.transfer


data class TransferState(

    var isTransferred : Boolean ?= false,
    var isLoading : Boolean = false,
    var error: String ?= null
)
