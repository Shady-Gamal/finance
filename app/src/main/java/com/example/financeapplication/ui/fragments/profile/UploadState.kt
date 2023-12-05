package com.example.financeapplication.ui.fragments.profile

import com.example.domain.entities.RecipientDTO

data class UploadState(
    var isUploaded : Boolean = false,
    var isLoading : Boolean = false,
    var error: String ?= null
)