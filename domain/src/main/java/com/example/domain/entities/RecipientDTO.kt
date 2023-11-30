package com.example.domain.entities

data class RecipientDTO(
    val userId : String = DataUtils.user?.id!!,
    val recipientId:String ?=null,
    val recipientFullName:String ?=null,
    val recipientPhoto : String ?= null
)