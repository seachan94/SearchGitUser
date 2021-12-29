package com.example.testyogiyo.data.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.data.local.UserEntity
import retrofit2.http.DELETE

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : UserEntity)

    @Query("DELETE FROM user WHERE id IN(:id) ")
    suspend fun deleteUser(id:String)

    @Query("SELECT * FROM user WHERE id LIKE '%' || :id || '%'")
    suspend fun getUser(id : String) : List<UserEntity>

    @Query("SELECT * FROM user")
    suspend fun getAllUser() : List<UserEntity>


}