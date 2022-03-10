package com.example.testyogiyo.ui.fragmentlocal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.meta.ResultState
import com.example.testyogiyo.data.remote.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor() : ViewModel(){

    private var _resultState = MutableLiveData<ResultState<List<User>>>(ResultState.Success(
        arrayListOf()))
    val resultState : LiveData<ResultState<List<User>>> = _resultState

    private var _errorMsg : String? = ""
    val errorMsg get() = _errorMsg

    val localUserData = MutableLiveData<List<User>>()
}