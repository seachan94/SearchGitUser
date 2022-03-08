package com.example.testyogiyo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testyogiyo.data.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : UserEntity)

    @Query("DELETE FROM user WHERE id IN(:id) ")
    suspend fun deleteUser(id:String)

    @Query("SELECT * FROM user WHERE id LIKE '%' || :id || '%'")
    suspend fun findUser(id : String) : List<UserEntity>

}