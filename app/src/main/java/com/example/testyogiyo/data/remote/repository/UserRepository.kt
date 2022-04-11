package com.example.testyogiyo.data.remote.repository

import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.response.GitResponse
import com.example.testyogiyo.data.remote.response.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getSearchUser(id : String, likedUser: MutableList<User>) : Flow<ResultState<List<User>>>
}