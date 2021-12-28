package com.example.testyogiyo.data.repository


import android.util.Log
import com.example.testyogiyo.data.GitResponse
import com.example.testyogiyo.data.NetworkStatus
import com.example.testyogiyo.data.remote.api.GithubApi
import com.example.testyogiyo.util.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApi : GithubApi
) :GithubRepository{

    override fun getSearchUser(id: String)=flow {

        val response : GitResponse = githubApi.getUser(id)

        if(response.total_count != 0){
            emit(NetworkStatus.Success(UserMapper.mapperToBook(response)))
        }
        else{
            emit(NetworkStatus.Error("no user"))
        }
    }.catch { e->
        Log.d("sechan", "getSearchUser: ${e.message}")
        emit(NetworkStatus.Error("network error"))
    }.flowOn(Dispatchers.IO)


}