package com.example.searchgituser.data.remote.repository

import androidx.paging.PagingData
import com.example.searchgituser.data.remote.response.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getSearchUser(id : String, likedUser: MutableList<User>) : Flow<PagingData<User>>
}