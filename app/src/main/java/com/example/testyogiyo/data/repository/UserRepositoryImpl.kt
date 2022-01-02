package com.example.testyogiyo.data.repository


import android.util.Log
import com.example.testyogiyo.data.DatabaseStatus
import com.example.testyogiyo.data.GitResponse
import com.example.testyogiyo.data.NetworkStatus
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.data.local.Dao.UserDao
import com.example.testyogiyo.data.local.UserEntity
import com.example.testyogiyo.data.remote.api.GithubApi
import com.example.testyogiyo.util.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi : GithubApi,
    private val databaseDao : UserDao
) :UserRepository{

    var localUser = arrayListOf<UserInfo>()

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



    override fun getAllUsersFromDb() = flow {
        localUser =  databaseDao.getAllUser().map {
            it.toUserInfo()
        } as ArrayList<UserInfo>

        Log.d("sechan", "getAllUsersFromDb: ${localUser}")
        emit(DatabaseStatus.Success(localUser))

    }.catch { e->
        emit(DatabaseStatus.Success(arrayListOf()))
    }.flowOn(Dispatchers.IO)

    override fun getUserFromDb(id : String)= flow{
        val response = databaseDao.getUser(id)
        emit(DatabaseStatus.Success(response))
    }.catch { e->
        emit(DatabaseStatus.Success(arrayListOf()))
    }.flowOn(Dispatchers.IO)

    override fun insertUserToDb(user: UserEntity) = flow{
        databaseDao.insertUser(user)
        localUser.add(user.toUserInfo())
        Log.d("sechan", "insertUserToDb: $localUser")
        emit(DatabaseStatus.Success(localUser))
    }.catch { e->
        emit(DatabaseStatus.Success(arrayListOf()))
    }.flowOn(Dispatchers.IO)

    override fun deleteUserFromDb(id: String) = flow{
        databaseDao.deleteUser(id)
        val userInfo = localUser.find { it.id == id }
        localUser.remove(userInfo)
        Log.d("sechan", "deleteUserFromDb: $localUser")
        emit(DatabaseStatus.Success(localUser))
    }.catch{e->
        emit(DatabaseStatus.Success(localUser))
    }.flowOn(Dispatchers.IO)


}