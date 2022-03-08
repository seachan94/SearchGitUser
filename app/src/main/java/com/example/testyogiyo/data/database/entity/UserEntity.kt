package com.example.testyogiyo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id : String,
    val img : String,
    val isLike : Boolean = true
)
