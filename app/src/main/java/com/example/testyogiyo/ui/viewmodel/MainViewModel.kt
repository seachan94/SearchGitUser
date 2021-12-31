package com.example.testyogiyo.ui.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyogiyo.data.NetworkStatus
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.data.local.Dao.UserDao
import com.example.testyogiyo.data.repository.UserRepository
import com.example.testyogiyo.ui.FragmentScreenA
import com.example.testyogiyo.ui.FragmentScreenB
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubApi: UserRepository,
    private val dao: UserDao,
) : ViewModel() {

    val searchText = MutableLiveData("")
    //val searchText = MutableStateFlow("")

    private var _userData = MutableLiveData(arrayListOf<UserInfo>())
    val userData: LiveData<ArrayList<UserInfo>> get() = _userData

    private var _resultState =
        MutableStateFlow<NetworkStatus<List<UserInfo>>>(NetworkStatus.Success(null))
    val resultState: StateFlow<NetworkStatus<List<UserInfo>>> get() = _resultState


    private var _localUserData = MutableLiveData(emptyList<UserInfo>())
    val localUserData: LiveData<List<UserInfo>> get() = _localUserData


    //tab layout에 따른 fragment 처리
    val listOfFragment = listOf(FragmentScreenA.newInstance(),FragmentScreenB.newInstance())
    var fragmentLayout: MutableLiveData<Fragment> = MutableLiveData(
        listOfFragment[0])

    val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            //tab이 null 나올 수 잇나?
            fragmentLayout.value = listOfFragment[tab?.position!!]
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    init{
        viewModelScope.launch {
            getAllUserFromDB()
        }
    }

    suspend fun requestUser() =
        githubApi.getSearchUser(searchText.value.toString()).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NetworkStatus.Loading
        ).collectLatest {
            when (it) {
                is NetworkStatus.Loading -> {
                    _resultState.value = NetworkStatus.Loading
                }
                is NetworkStatus.Error -> {
                    _resultState.value = NetworkStatus.Error(it.message!!)
                }
                is NetworkStatus.Success -> {
                    _userData.value = it.data?.user
                    //api받아 왔을 떄 db 값과 비교
                    localUserData.value?.forEach { db->
                        userData.value?.forEachIndexed  { idx,api->
                            if (db.id == api.id){
                                _userData.value!![idx] = db
                            }
                        }
                    }
                    _resultState.value = NetworkStatus.Success(_userData.value)
                }
            }
        }

    suspend fun insertUserToDB(position: Int) =
        dao.insertUser(userData.value!!.get(position).toEntitiy())

    suspend fun getAllUserFromDB() =
        githubApi.getAllUsersFromDb().collect {
            _localUserData.value = it.data!!.map {
                it.toUserInfo()
            }
        }
    suspend fun deleteUserFromDB(position: Int)=
        dao.deleteUser(localUserData.value!!.get(position).id)

    suspend fun getUserFromDB() =
        githubApi.getUserFromDb(searchText.value!!).collect {
            _localUserData.value = it.data!!.map{
                it.toUserInfo()
            }
        }


    fun toggleUserDataLike( position: Int, isLike : Boolean, isLocal : Boolean){
        if(!isLocal) {
            _userData.value!!.get(position).isLike = isLike
        }else{
            val changedLocalUser = _localUserData.value!!.get(position)
            localUserData.value!!.get(position).isLike = isLike
            userData.value?.forEachIndexed  { idx,api->
                if (changedLocalUser.id == api.id){
                    _userData.value!![idx] = changedLocalUser
                }
            }
        }

        if(isLike){
            viewModelScope.launch {
                insertUserToDB(position)
            }
        }else{
           viewModelScope.launch {
               deleteUserFromDB(position)
           }
        }
    }

    fun setUserDataLike(position: Int,isLike: Boolean) {
        _userData.value?.get(position)!!.isLike = isLike
    }
}


