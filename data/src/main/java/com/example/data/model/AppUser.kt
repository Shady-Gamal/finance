package com.example.data.model

import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.BankingInfoDTO

data class AppUser(

    var id : String ?= null,
    var fullName : String ?= null,
    var email : String ?= null,
    var profilePictureUrl : String ?= null,
    var bankingInfo: BankingInfo = BankingInfo(),
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

data class BankingInfo(
    val salary : Int = 0,
    val totalTransfers : Int =0,
    val availableBalance : Int =0,
)
fun AppUser.toAppUserDTO() : AppUserDTO {

    return AppUserDTO(
        id = id,
        fullName = fullName,
        email = email,
        profilePictureUrl = profilePictureUrl,
        bankingInfo = bankingInfo.toBankingInfoDTO(),
        phoneNumber = phoneNumber,
        address = address,
        country = country,
        optionalAddress = optionalAddress,
        zipCode = zipCode

        )
}

fun BankingInfo.toBankingInfoDTO() : BankingInfoDTO {

    return BankingInfoDTO(
        salary = salary,
        totalTransfers = totalTransfers,
        availableBalance = availableBalance
    )
}
