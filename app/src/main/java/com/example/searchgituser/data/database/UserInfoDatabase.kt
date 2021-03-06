package com.example.searchgituser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.searchgituser.data.database.entity.UserEntity
import com.example.searchgituser.data.database.dao.UserDao


@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UserInfoDatabase : RoomDatabase(){
    abstract val userDao : UserDao
}