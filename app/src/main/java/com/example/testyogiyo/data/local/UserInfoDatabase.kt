package com.example.testyogiyo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testyogiyo.data.local.Dao.UserDao


@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UserInfoDatabase : RoomDatabase(){

    abstract val userDao : UserDao

}