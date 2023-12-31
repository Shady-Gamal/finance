package com.example.domain.entities

data class TransactionDTO(
    var id : String ?= null,
    val senderId : String ?= null,
    val senderName : String ?= null,
    val receiverId : String ?= null,
    val receiverName : String ?= null,
    val value : Double ?= null,
    val profilePic : String ?= null,
    var isSender : Boolean ?= true,
    val date: Long ?= System.currentTimeMillis()
)
