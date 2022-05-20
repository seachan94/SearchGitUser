package com.example.searchgituser.ui.fragmentapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchgituser.data.database.repository.LocalUserRepository
import com.example.searchgituser.data.remote.repository.UserRepository
import com.example.searchgituser.data.remote.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localUserRepository: LocalUserRepository
) : ViewModel(){
    var remoteUser = MutableLiveData<List<User>>()
}