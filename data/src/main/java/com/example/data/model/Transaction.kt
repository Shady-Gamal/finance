package com.example.data.model

import com.example.domain.entities.TransactionDTO

data class Transaction(
    val id : String ?= null,
    val senderId : String ?= null,
    val senderName : String ?= null,
    val receiverId : String ?= null,
    val receiverName: String ?=null,
    val profilePic : String ?= null,
    val value : Double ?= null,
    var isSender : Boolean ?= true,
    val date: Long ?= System.currentTimeMillis()
)

fun Transaction.toTransactionDTO() : TransactionDTO {

    return TransactionDTO(
        id = id,
        senderId =senderId,
        receiverId = receiverId,
        senderName = senderName,
        receiverName = receiverName,
        profilePic = profilePic,
        value = value,
        date = date,
        isSender = isSender
    )


}
