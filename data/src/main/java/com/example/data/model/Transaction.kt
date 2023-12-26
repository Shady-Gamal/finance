package com.example.data.model

data class Transaction(
    val id : String ?= null,
    val senderId : String ?= null,
    val receiverId : String ?= null,
    val value : Double ?= null,
    val date: Long ?= System.currentTimeMillis()
)
