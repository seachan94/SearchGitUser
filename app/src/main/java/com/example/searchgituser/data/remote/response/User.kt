package com.example.searchgituser.data.remote.response

import com.example.searchgituser.data.database.entity.UserEntity

data class User(
    val avatar_url : String,
    val login : String,
    var isLike : Boolean = false
){
    override fun equals(other: Any?): Boolean =
        login == (other as User).login && avatar_url == (other as User).avatar_url
    fun toEntityFromUser() = UserEntity(
        login,
        avatar_url
    )
}
