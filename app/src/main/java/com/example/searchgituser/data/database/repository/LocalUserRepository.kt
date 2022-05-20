package com.example.searchgituser.data.database.repository

import com.example.searchgituser.data.database.entity.UserEntity
import com.example.searchgituser.data.meta.ResultState
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    suspend fun insertUser(user : UserEntity)
    suspend fun deleteUser(id : String)
    suspend fun findUser(id : String) : Flow<ResultState<List<UserEntity>>>
    suspend fun getAllUser() : Flow<List<UserEntity>>
}