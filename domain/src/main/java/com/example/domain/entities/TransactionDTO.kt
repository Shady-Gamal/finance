package com.example.domain.entities

data class TransactionDTO(
    var id : String ?= null,
    val senderId : String ?= null,
    val receiverId : String ?= null,
    val value : Double ?= null,
    val date: Long ?= System.currentTimeMillis()
)
