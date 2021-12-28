package com.example.testyogiyo.data.repository

import com.example.testyogiyo.data.NetworkStatus
import com.example.testyogiyo.data.Users
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getSearchUser(id : String) : Flow<NetworkStatus<Users>>
}