package com.example.testyogiyo.data.remote

import com.example.testyogiyo.data.remote.response.GitResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getUser(
        @Query("q") q: String
    ) : GitResponse

}