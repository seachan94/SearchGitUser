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
        MutableLiveData<ResultState<List<User>>>(ResultState.Success(listOf()))
    val resultState: LiveData<ResultState<List<User>>> = _resultState

    private var _errorMsg: String? = ""
    val errorMsg = _errorMsg

    val searchText = MutableStateFlow("")

    private var allLocalUser = MutableStateFlow(mutableListOf<User>())
    var remoteUser = MutableLiveData(listOf<User>())
    var localUsers = MutableLiveData(listOf<User>())


    var testlocallUser = allLocalUser.asStateFlow()

    fun emitUserToLocal() = viewModelScope.launch {
        allLocalUser.emit(allLocalUser.value.toMutableList())
        //Log.d("sechan", "emitUserToLocal: ${testlocallUser.value}")
    }

    init {
        getAllUserFromLocal()
    }

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
            id.uppercase() == it.login.uppercase()
        }
    }

    fun insertUserToLocal(user: User) = viewModelScope.launch {
        //localUserRepository.insertUser(user.toEntityFromUser())
        allLocalUser.value.add(user)
        localUsers.value = allLocalUser.value
        emitUserToLocal()
    }


    fun deleteUserFromLocal(id: String) = viewModelScope.launch {
        localUserRepository.deleteUser(id)
        localUsers.value = localUsers.value?.filterNot { it.login == id }?.toList()
        remoteUser.value = remoteUser.value?.map {
            if (it.login == id) {
                it.isLike = false
            }
            it
        }?.toList()
        allLocalUser.value = allLocalUser.value.filterNot { it.login == id }.toMutableList()

    }

    private fun getAllUserFromLocal() =
        viewModelScope.launch {
            localUserRepository.getAllUser().collect { users ->
                allLocalUser.value = users.map { it.toUserFromUserEntity() }.toMutableList()
                localUsers.value = allLocalUser.value
            }
        }

    val test1 = MutableStateFlow(listOf(1,2,3,4,5,6,7))
    val test2 = test1.asStateFlow()

    fun a() = viewModelScope.launch{
        test1.emit(
            test1.value.filter{ it%2 == 0}.map{
                it+1
            }
        )
    }

}

