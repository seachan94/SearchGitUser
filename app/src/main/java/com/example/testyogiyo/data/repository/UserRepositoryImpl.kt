package com.example.testyogiyo.data.repository


import android.util.Log
import androidx.annotation.WorkerThread
import com.example.testyogiyo.data.DatabaseStatus
import com.example.testyogiyo.data.GitResponse
import com.example.testyogiyo.data.NetworkStatus
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.data.local.Dao.UserDao
import com.example.testyogiyo.data.local.UserEntity
import com.example.testyogiyo.data.remote.api.GithubApi
import com.example.testyogiyo.util.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi : GithubApi,
    private val databaseDao : UserDao
) :UserRepository{

    override fun getSearchUser(id: String)=flow {

        val response : GitResponse = githubApi.getUser(id)

        if(response.total_count != 0){
            emit(NetworkStatus.Success(UserMapper.mapperToBook(response)))
        }
        else{
            emit(NetworkStatus.Error("no user"))
        }
    }.catch { e->
        emit(NetworkStatus.Error("network error"))
    }.flowOn(Dispatchers.IO)



    override fun getAllUsersFromDb()=flow {
        val response = databaseDao.getAllUser()
        emit(DatabaseStatus.Success(response))
    }.catch { e->
        emit(DatabaseStatus.Success(emptyList()))
    }.flowOn(Dispatchers.IO)

    override fun getUserFromDb(id : String)= flow{
        val response = databaseDao.getUser(id)
        emit(DatabaseStatus.Success(response))
    }.catch { e->
        emit(DatabaseStatus.Success(arrayListOf()))
    }.flowOn(Dispatchers.IO)

    override suspend fun insertUserToDb(user: UserEntity) {
        databaseDao.insertUser(user)
    }

    override suspend fun deleteUserFromDb(id: String) {
        databaseDao.deleteUser(id)
    }


}