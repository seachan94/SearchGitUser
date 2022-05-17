package com.example.testyogiyo.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
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

    private val _resultState =
        MutableLiveData<PagingData<User>>(PagingData.empty())
    val resultState: LiveData<PagingData<User>> = _resultState

    private var _errorMsg: String? = ""
    val errorMsg = _errorMsg

    val searchText = MutableStateFlow("")

    var allLocalUser = MutableStateFlow(mutableListOf<User>())
    var remoteUser = MutableLiveData(listOf<User>())
    var localUsers = MutableLiveData(listOf<User>())

    init { getAllUserFromLocal() }

    fun getUserFromRemote(id: String) = viewModelScope.launch {
        apiUserRepository.getSearchUser(id, allLocalUser.value)
            .cachedIn(viewModelScope)
            .collectLatest {
                _resultState.value = it
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
        localUsers.value = allLocalUser.value
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

    fun getAllUserFromLocal() =
        viewModelScope.launch {
            localUserRepository.getAllUser().collect { users ->
                allLocalUser.value = users.map { it.toUserFromUserEntity() }.toMutableList()
                localUsers.value = allLocalUser.value.toList()
            }
        }
}

