package com.example.testyogiyo.repository

import androidx.paging.PagingData
import com.example.testyogiyo.data.remote.repository.UserRepository
import com.example.testyogiyo.data.remote.response.User
import kotlinx.coroutines.flow.Flow

class FakeApiUserRepository : UserRepository {
    override fun getSearchUser(id: String, likedUser: MutableList<User>): Flow<PagingData<User>> {
        TODO("Not yet implemented")
    }
}