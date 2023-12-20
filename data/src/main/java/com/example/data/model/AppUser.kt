package com.example.data.model

import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.FinanceDTO

data class AppUser(

    var id : String ?= null,
    var fullName : String ?= null,
    var email : String ?= null,
    var profilePictureUrl : String ?= null,
    var country : String ?= null,
    var zipCode : String ?= null,
    var address : String ?= null,
    var phoneNumber : String = "000000000000",
    var optionalAddress : String ?=null,


    ){
    companion object{
        val COLLECTION_NAME = "Users"

    }
}

data class Finance(
    val salary : Int = 0,
    val totalTransfers : Int =0,
    val balance : Double =0.0,
) {
    companion object {
        val COLLECTION_NAME = "Finance"

    }
}

fun AppUser.toAppUserDTO() : AppUserDTO {

    return AppUserDTO(
        id = id,
        fullName = fullName,
        email = email,
        profilePictureUrl = profilePictureUrl,
        phoneNumber = phoneNumber,
        address = address,
        country = country,
        optionalAddress = optionalAddress,
        zipCode = zipCode

        )
}

fun Finance.toFinanceDTO() : FinanceDTO {

    return FinanceDTO(
        salary = salary,
        totalTransfers = totalTransfers,
        balance = balance
    )
}
