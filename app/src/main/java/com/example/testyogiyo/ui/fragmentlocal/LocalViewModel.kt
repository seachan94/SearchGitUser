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
class LocalViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository
) : ViewModel(){

    private var _resultState = MutableLiveData<ResultState<List<User>>>(ResultState.Success(null))
    val resultState : LiveData<ResultState<List<User>>> = _resultState

    private var _errorMsg : String? = ""
    val errorMsg get() = _errorMsg

    fun getUserFromLocal(id : String) = viewModelScope.launch{
        localUserRepository.findUser(id).collectLatest{
            when(it){
                is ResultState.Loading -> { _resultState.value = it }
                is ResultState.Error ->{ _errorMsg = it.message }
                is ResultState.Success->{
                    _resultState.value = ResultState.Success(it.data?.map{
                        it.toUserFromUserEntity()
                    })
                }
            }
        }
    }

    fun insertUserToLocal(user : User) = viewModelScope.launch{
        localUserRepository.insertUser(user.toEntityFromUser())
        getAllUser()
    }
    fun deleteUserFromLocal(id : String) = viewModelScope.launch{
        localUserRepository.deleteUser(id)
        getAllUser()
    }
    fun getAllUser() = viewModelScope.launch{

        localUserRepository.getAllUser().collect {
            Log.d("sechan", "getAllUser: $it")
        }


    }
}