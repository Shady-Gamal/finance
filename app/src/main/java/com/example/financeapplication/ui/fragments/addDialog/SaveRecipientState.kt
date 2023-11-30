package com.example.financeapplication.ui.fragments.addDialog

data class SaveRecipientState(
    var isSaved : Boolean = false,
    var isLoading : Boolean = false,
    var error: String ?= null
)