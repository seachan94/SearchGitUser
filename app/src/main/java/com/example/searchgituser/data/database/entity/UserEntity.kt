package com.example.searchgituser.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.searchgituser.data.remote.response.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id : String,
    val img : String,
){
    fun toUserFromUserEntity() = User(
        img,
        id,
        true
    )
}
