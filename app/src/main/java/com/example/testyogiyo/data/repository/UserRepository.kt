package com.example.testyogiyo.data.repository

import androidx.annotation.WorkerThread
import com.example.testyogiyo.data.DatabaseStatus
import com.example.testyogiyo.data.NetworkStatus
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.data.Users
import com.example.testyogiyo.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getSearchUser(id : String) : Flow<NetworkStatus<Users>>

    fun getAllUsersFromDb() : Flow<DatabaseStatus.Success<List<UserInfo>>>
    fun getUserFromDb(id : String) : Flow<DatabaseStatus.Success<List<UserEntity>>>

    fun insertUserToDb(user : UserEntity) : Flow<DatabaseStatus.Success<ArrayList<UserInfo>>>
    fun deleteUserFromDb(id : String) : Flow<DatabaseStatus.Success<ArrayList<UserInfo>>>

}