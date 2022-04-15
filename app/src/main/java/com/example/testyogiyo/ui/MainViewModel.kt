package com.example.testyogiyo.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.repository.UserRepository
import com.example.testyogiyo.data.remote.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiUserRepository: UserRepository,
    private val localUserRepository: LocalUserRepository,
) : ViewModel() {

    init {
        getAllUserFromLocal()
    }

    private val _resultState =
        MutableLiveData<ResultState<List<User>>>(ResultState.Success(listOf()))
    val resultState: LiveData<ResultState<List<User>>> = _resultState

    private var _errorMsg: String? = ""
    val errorMsg = _errorMsg

    val searchText = MutableStateFlow("")

    var allLocalUser = MutableStateFlow(mutableListOf<User>())
    var remoteUser = MutableLiveData(listOf<User>())
    var localUsers = MutableLiveData(listOf<User>())

    fun getUserFromRemote(id: String) = viewModelScope.launch {
        apiUserRepository.getSearchUser(id, allLocalUser.value).collectLatest {
            when (it) {
                is ResultState.Loading -> {
                    _resultState.value = ResultState.Loading
                }
                is ResultState.Error -> {
                    _errorMsg = it.message
                }
                is ResultState.Success -> {
                    _resultState.value = it
                    remoteUser.value = it.data
                }
            }
        }
    }

    fun getUserFromLocal(id: String) {
        localUsers.value = allLocalUser.value.filter {
            it.login.uppercase().contains(id.uppercase())
        }
    }

    fun insertUserToLocal(user: User) = viewModelScope.launch {
        localUserRepository.insertUser(user.toEntityFromUser())
        allLocalUser.value.add(user)
    }

    fun deleteUserFromLocal(user: User) = viewModelScope.launch {
        localUserRepository.deleteUser(user.login)
        remoteUser.value = remoteUser.value?.map {
            if (it.login == user.login) {
                it.isLike = false
            }
            it
        }?.toList()
        allLocalUser.value.remove(user)
    }

    private fun getAllUserFromLocal() =
        viewModelScope.launch {
            localUserRepository.getAllUser().collect { users ->
                allLocalUser.value = users.map { it.toUserFromUserEntity() }.toMutableList()
                localUsers.value = allLocalUser.value.toList()
            }
        }
}

