package com.example.testyogiyo.repository

import com.example.testyogiyo.data.database.entity.UserEntity
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class FakeLocalUserRepository : LocalUserRepository{
    private var userList = mutableListOf<UserEntity>()
    private var isNetworkStatus = false

    fun setNetworkState(value : Boolean){
        isNetworkStatus = value
    }

    fun setUserList(data : MutableList<UserEntity>){
        userList = data
    }
    override suspend fun insertUser(user: UserEntity) {
        userList.add(user)
    }

    override suspend fun deleteUser(id: String) {
       userList =  userList.filterNot{ it.id == id }.toMutableList()
    }

    override suspend fun findUser(id: String)= flow{
        if(isNetworkStatus){
            emit(ResultState.Success(userList))
        }else{
            emit(ResultState.Error("ERROR",Throwable()))
        }
    }

    override suspend fun getAllUser(): Flow<List<UserEntity>> =flow {
        emit(listOf())
    }


}