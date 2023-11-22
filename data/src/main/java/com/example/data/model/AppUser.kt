package com.example.data.model

import com.example.domain.entities.AppUserDTO

data class AppUser(

    var id : String ?= null,
    var fullName : String ?= null,
    var email : String ?= null,


    ){
    companion object{
        val COLLECTION_NAME = "Users"

    }
}
fun AppUser.toAppUserDTO() : AppUserDTO {

    return AppUserDTO(
        id = id,
        fullName = fullName,
        email = email
    )
}
