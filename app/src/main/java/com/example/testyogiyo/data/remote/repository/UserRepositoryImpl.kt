package com.example.testyogiyo.data.remote.repository


import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.GithubApi
import com.example.testyogiyo.data.remote.response.GitResponse
import com.example.testyogiyo.data.remote.response.User
import com.example.testyogiyo.data.source.paging.GitPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
) : UserRepository {
    //    override fun getSearchUser(
//        id: String,
//        likedUser: MutableList<User>,
//    ): Flow<ResultState<List<User>>> = flow {
//        emit(ResultState.Loading)
//        val response = githubApi.getUser(id)
//
//        val checkedCompletedUser = response.items.map {
//            if(likedUser.contains(it)){ it.isLike = true }
//            it
//        }
//
//        emit(ResultState.Success(checkedCompletedUser))
//    }.catch { e ->
//        emit(ResultState.Error(error = e))
//    }
    override fun getSearchUser(
        id: String,
        likedUser: MutableList<User>,
    ): Flow<PagingData<User>> {
        Log.d("sechan", "getSearchUser repo: $id")
        return Pager(
            config = PagingConfig(
                pageSize = GitPagingSource.defaultDisplay,
                enablePlaceholders = false,
                initialLoadSize = GitPagingSource.defaultDisplay
            ),
            pagingSourceFactory = {
                GitPagingSource(githubApi, id, likedUser)
            }
        ).flow
    }
}