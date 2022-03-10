package com.example.testyogiyo.ui.fragmentapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyogiyo.data.database.entity.UserEntity
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.database.repository.LocalUserRepositoryImpl
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.repository.UserRepository
import com.example.testyogiyo.data.remote.response.GitResponse
import com.example.testyogiyo.data.remote.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localUserRepository: LocalUserRepository
) : ViewModel(){
    var remoteUser = MutableLiveData<List<User>>()
}