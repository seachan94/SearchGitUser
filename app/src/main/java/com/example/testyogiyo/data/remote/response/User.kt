package com.example.testyogiyo.data.remote.response

import com.example.testyogiyo.data.database.entity.UserEntity

data class User(
    val avatar_url : String,
    val login : String,
    var isLike : Boolean = false
){
    fun toEntityFromUser() = UserEntity(
        login,
        avatar_url
    )
}
