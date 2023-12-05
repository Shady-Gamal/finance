package com.example.data.model

import com.example.domain.entities.DataUtils.user
import com.example.domain.entities.RecipientDTO

data class Recipient(
    val userId : String = user?.id!!,
    val recipientId:String ?=null,
    val recipientFullName:String ?=null,
    val recipientPhoto : String ?= null
){
    companion object{
        val COLLECTION_NAME = "Recipients"

    }
}

fun Recipient.toARecipientDTO() : RecipientDTO {

    return RecipientDTO(
        recipientId = recipientId,
        recipientFullName = recipientFullName,
        recipientPhoto = recipientPhoto,
        userId = userId

    )
}