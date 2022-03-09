package com.example.testyogiyo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testyogiyo.data.database.entity.UserEntity
import com.example.testyogiyo.data.remote.response.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : UserEntity)

    @Query("DELETE FROM user WHERE id IN(:id) ")
    suspend fun deleteUser(id:String):Int

    @Query("SELECT * FROM user WHERE id LIKE '%' || :id || '%'")
    suspend fun findUser(id : String) : List<UserEntity>

    @Query("SELECT * FROM user")
    suspend fun getAllUser() : List<UserEntity>

}