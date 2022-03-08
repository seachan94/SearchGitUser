package com.example.testyogiyo.data.remote.repository


import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.GithubApi
import com.example.testyogiyo.data.remote.response.GitResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi : GithubApi
) : UserRepository {
    override fun getSearchUser(id: String): Flow<ResultState<GitResponse>> = flow{
        emit(ResultState.Loading)
        val response = githubApi.getUser(id)
        emit(ResultState.Success(response))
    }.catch { e->
        emit(ResultState.Error(error = e))
    }

}