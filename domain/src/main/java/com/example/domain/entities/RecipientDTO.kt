package com.example.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RecipientDTO (
    val userId : String = DataUtils.user?.value?.id!!,
    val recipientId:String ?=null,
    val recipientFullName:String ?=null,
    val recipientPhoto : String ?= null
 ) : Parcelable