package com.example.domain.entities

data class AppUserDTO(
    var id : String ?= null,
var fullName : String ?= null,
var email : String ?= null,
var bankingInfo: BankingInfoDTO = BankingInfoDTO()
)

data class BankingInfoDTO(
    val salary : Int = 0,
    val totalTransfers : Int =0,
    val availableBalance : Int =0,
)

object DataUtils {
        var user : AppUserDTO ?= null
    }


