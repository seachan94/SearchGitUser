package com.example.testyogiyo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testyogiyo.data.UserInfo

@Entity(tableName = "user")
data class UserEntity(
    val img : String,
    @PrimaryKey val id : String,
    val isLike : Boolean
){
    fun toUserInfo() : UserInfo=
        UserInfo(
            img =img,
            id =id,
            isLike
        )
}
