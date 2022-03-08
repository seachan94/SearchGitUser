package com.example.testyogiyo.ui.fragmentapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.database.repository.LocalUserRepositoryImpl
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.repository.UserRepository
import com.example.testyogiyo.data.remote.response.GitResponse
import com.example.testyogiyo.data.remote.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    private var _resultState = MutableLiveData<ResultState<GitResponse>>(ResultState.Success(null))
    val resultState : LiveData<ResultState<GitResponse>> = _resultState

    private var _errorMsg : String? = ""
    val errorMsg get() = _errorMsg


    init {

        viewModelScope.launch{

        }
    }
    fun findUserFromRemoteRepository(id : String) = viewModelScope.launch{
        userRepository.getSearchUser(id).collectLatest {
            Log.d("sechan", "findUserFromRemoteRepository: ")
            when(it){
                is ResultState.Loading ->{_resultState.value = ResultState.Loading}
                is ResultState.Error -> {
                    _resultState.value = it
                    _errorMsg = it.message
                }
                is ResultState.Success ->{
                    Log.d("sechan", "findUserFromRemoteRepository: ${it.data}")
                    _resultState.value = it
                }
            }
        }
    }
}