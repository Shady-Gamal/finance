package com.example.data.model

import com.example.domain.entities.DataUtils.user
import com.example.domain.entities.RecipientDTO

data class Recipient(
    val userId : String = user?.value?.id!!,
    val recipientId:String ?=null,
    val recipientFullName:String ?=null,
    val recipientProfilePictureUrl: String ?= null
){
    companion object{
        val COLLECTION_NAME = "Recipients"

    }
}

fun Recipient.toARecipientDTO() : RecipientDTO {

    return RecipientDTO(
        recipientId = recipientId,
        recipientFullName = recipientFullName,
        recipientPhoto = recipientProfilePictureUrl,
        userId = userId

    )
}
