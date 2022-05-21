package com.example.searchgituser.data.remote.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchgituser.data.remote.GithubApi
import com.example.searchgituser.data.remote.response.User
import com.example.searchgituser.data.source.paging.GitPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
) : UserRepository {

    override fun getSearchUser(
        id: String,
        likedUser: MutableList<User>,
    ): Flow<PagingData<User>> {
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