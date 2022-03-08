package com.example.testyogiyo.data.database.repository

import com.example.testyogiyo.data.database.entity.UserEntity
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.response.User
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    suspend fun insertUser(user : UserEntity)
    suspend fun deleteUser(id : String)
    suspend fun findUser(id : String) : Flow<ResultState<List<UserEntity>>>
}