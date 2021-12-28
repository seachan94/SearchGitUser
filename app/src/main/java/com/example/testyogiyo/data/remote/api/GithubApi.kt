package com.example.testyogiyo.data.remote.api

import com.example.testyogiyo.data.GitResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getUser(
        @Query("q") q: String
    ) : GitResponse

}