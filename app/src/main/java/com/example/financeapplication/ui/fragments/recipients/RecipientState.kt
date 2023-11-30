package com.example.financeapplication.ui.fragments.recipients


import com.example.domain.entities.RecipientDTO

data class RecipientState(

    var recipientsInfo : MutableList<RecipientDTO> ?= null,
    var isLoading : Boolean = false,
    var error: String ?= null
)
