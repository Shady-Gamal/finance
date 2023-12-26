package com.example.financeapplication.ui.fragments.addDialog


import com.example.domain.entities.RecipientDTO

data class QrCodeScanState(

    var isScanned : Boolean = false,
    var isLoading : Boolean = false,
    var error: String ?= null
)
