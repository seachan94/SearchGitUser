package com.example.testyogiyo.data.database.repository

import android.util.Log
import com.example.testyogiyo.data.database.dao.UserDao
import com.example.testyogiyo.data.database.entity.UserEntity
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.response.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val userDao : UserDao
) :LocalUserRepository {

    override suspend fun insertUser(user: UserEntity) { userDao.insertUser(user) }

    override suspend fun deleteUser(id: String) {  userDao.deleteUser(id) }

    override suspend fun findUser(id: String): Flow<ResultState<List<UserEntity>>> = flow{
        emit(ResultState.Loading)
        val response = userDao.findUser(id)
        emit(ResultState.Success(response))
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllUser(): Flow<List<UserEntity>> = flow{
        val respone = userDao.getAllUser()
        emit(respone)
    }
}