package com.example.domain.entities


import kotlinx.coroutines.flow.MutableStateFlow

data class AppUserDTO(
    var id : String ?= null,
    var fullName : String ?= null,
    var email : String ?= null,
    var profilePictureUrl : String ?=null,
    var phoneNumber : String ?= null,
    var country : String ?= null,
    var zipCode : String ?= null,
    var address : String ?= null,
    var optionalAddress : String ?=null,
    )

data class FinanceDTO(
    val salary : Int = 0,
    val totalTransfers : Int =0,
    val balance : Double =0.0,
)

object DataUtils {
        var user : MutableStateFlow<AppUserDTO?>? = MutableStateFlow(null)
    }


