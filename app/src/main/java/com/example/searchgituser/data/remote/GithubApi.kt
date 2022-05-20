package com.example.searchgituser.data.remote

import com.example.searchgituser.data.remote.response.GitResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getUser(
        @Query("q") q: String,
        @Query("per_page") per : Int,
        @Query("page") page : Int
    ) : GitResponse

}