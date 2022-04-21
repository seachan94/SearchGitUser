package com.example.testyogiyo.data.source.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testyogiyo.data.remote.GithubApi
import com.example.testyogiyo.data.remote.response.GitResponse
import com.example.testyogiyo.data.remote.response.User
import java.lang.Exception
import javax.inject.Inject

class GitPagingSource(
    private val githubApi: GithubApi,
    private val searchText : String,
    private val likedUsers: MutableList<User>
) : PagingSource<Int, User>()  {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> =
        try{
            val pageNumber = params.key ?: 1
            val response = githubApi.getUser(searchText, defaultDisplay,pageNumber)

            val data = checkLike(response,likedUsers)
            Log.d("sechan", "load: $pageNumber ${data.isEmpty()}")
            LoadResult.Page(
                data = data,
                prevKey = if(pageNumber ==1 ) null else pageNumber-1,
                nextKey = if(data.isEmpty()) null else pageNumber+1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }

    private fun checkLike(response : GitResponse, likedUsers : MutableList<User>) =
        response.items.map{
            if(likedUsers.contains(it)){
                it.isLike = true
            }
            it
        }

    companion object {
        const val defaultStart = 1
        const val defaultDisplay = 10
    }
}