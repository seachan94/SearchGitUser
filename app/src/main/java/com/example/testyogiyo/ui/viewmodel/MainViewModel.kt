package com.example.testyogiyo.ui.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyogiyo.data.NetworkStatus
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.data.repository.GithubRepository
import com.example.testyogiyo.ui.FragmentScreenA
import com.example.testyogiyo.ui.FragmentScreenB
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubApi : GithubRepository
):  ViewModel() {

    val searchText = MutableStateFlow("")

    private var _userData = MutableLiveData(emptyList<UserInfo>())
    val userData : LiveData<List<UserInfo> > get() = _userData

    private var _resultState = MutableStateFlow<NetworkStatus<List<UserInfo>>>(NetworkStatus.Success(null))
    val resultState : StateFlow<NetworkStatus<List<UserInfo>>> get() = _resultState

    var fragmentLayout : MutableLiveData<Fragment> = MutableLiveData(FragmentScreenA.newInstance())

    val tabSelectedListener = object : TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            when(tab?.position){
                0->{fragmentLayout.value =  FragmentScreenA.newInstance()}
                1->{fragmentLayout.value =  FragmentScreenB.newInstance()}
            }
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    suspend fun requestUser()=
        githubApi.getSearchUser(searchText.value).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NetworkStatus.Loading
        ).collect {
            when(it){
                is NetworkStatus.Loading ->{
                    _resultState.value =NetworkStatus.Loading
                }
                is NetworkStatus.Error ->{
                    _resultState.value = NetworkStatus.Error(it.message!!)
                }
                is NetworkStatus.Success->{
                    _userData.value = it.data?.user
                    _resultState.value = NetworkStatus.Success(_userData.value)
                    Log.d("sechan", "requestuser: ${_resultState.value}")
                }
            }
        }



}