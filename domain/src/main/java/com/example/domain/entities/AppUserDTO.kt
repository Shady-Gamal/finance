package com.example.domain.entities

data class AppUserDTO(

var id : String ?= null,
var fullName : String ?= null,
var email : String ?= null,

)

object DataUtils {
        var user : AppUserDTO ?= null
    }


