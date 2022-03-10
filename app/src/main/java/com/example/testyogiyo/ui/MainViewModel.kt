package com.example.testyogiyo.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.repository.UserRepository
import com.example.testyogiyo.data.remote.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiUserRepository: UserRepository,
    private val localUserRepository: LocalUserRepository,
) : ViewModel() {

    private val _resultState =
        MutableLiveData<ResultState<List<User>>>(ResultState.Success(listOf()))
    val resultState: LiveData<ResultState<List<User>>> = _resultState

    private var _errorMsg: String? = ""
    val errorMsg get() = _errorMsg

    val searchText = MutableStateFlow("")

    var allLocalUser = mutableListOf<User>()
    var remoteUser = MutableLiveData(listOf<User>())
    var localUsers = MutableLiveData(listOf<User>())

    init {
        getAllUserFromLocal()
    }

    fun getUserFromRemote(id: String) = viewModelScope.launch {
        apiUserRepository.getSearchUser(id).collectLatest { it ->
            when (it) {
                is ResultState.Loading -> { _resultState.value = ResultState.Loading }
                is ResultState.Error -> { _errorMsg = it.message }
                is ResultState.Success -> {
                    remoteUser.value = it.data?.items.map { remoteUser ->
                        if (allLocalUser.contains(remoteUser))
                            remoteUser.copy(
                                avatar_url = remoteUser.avatar_url,
                                login = remoteUser.login,
                                isLike = true)
                        else
                            remoteUser
                    }
                }
            }
        }
    }

    fun getUserFromLocal(id: String) {
        localUsers.value = allLocalUser.filter {
            id.uppercase() == it.login.uppercase()
        }
    }

    fun insertUserToLocal(user: User) = viewModelScope.launch {
        localUserRepository.insertUser(user.toEntityFromUser())
        allLocalUser.add(user)
    }


    fun deleteUserFromLocal(id: String) = viewModelScope.launch {
        localUserRepository.deleteUser(id)
        allLocalUser = allLocalUser.filterNot {
            it.login == id
        }.toMutableList()
        localUsers.value?.filterNot { it.login == id }?.toList()
    }

    private fun getAllUserFromLocal() =
        viewModelScope.launch {
            localUserRepository.getAllUser().collect {
                allLocalUser = it.map { it.toUserFromUserEntity() }.toMutableList()
            }
        }
}

