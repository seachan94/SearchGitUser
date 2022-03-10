package com.example.testyogiyo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.testyogiyo.R
import com.example.testyogiyo.databinding.ActivityMainBinding
import com.example.testyogiyo.ui.fragmentapi.ApiFragment
import com.example.testyogiyo.ui.fragmentlocal.LocalFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    private val apiFragment by lazy { ApiFragment.newInstance() }
    private val localFragment by lazy{ LocalFragment.newInstance() }

    //어떠한 Tab (fragment)가 attach 되어 있는지 확인 한다.
    private var attachFragmentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply{
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }
        setContentView(binding.root)
        fragmentLayout()
        observeSearchText()
    }

    private fun fragmentLayout(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout,apiFragment)
            .commit()

        binding.tabLayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) { replaceView(tab!!.position) }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun replaceView(position : Int){
        attachFragmentPosition = position
        when(position){
            0->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout,apiFragment)
                    .commit()
            }
            1->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout,localFragment)
                    .commit()
            }
        }
    }

    private fun observeSearchText()  = lifecycleScope.launchWhenResumed {
        viewModel.searchText.debounce(500L).collectLatest {
            if(it.isNotEmpty()){
                when(attachFragmentPosition){
                    0-> { viewModel.getUserFromRemote(viewModel.searchText.value) }
                    1->{ viewModel.getUserFromLocal(viewModel.searchText.value) }
                }
            }
        }


    }
    fun print(tab : Int){
        val TAG = "sechan"
        Log.d(TAG, "print: tab : $tab")
        Log.d(TAG, "print: all ${viewModel.allLocalUser}")
        Log.d(TAG, "print: remote ${viewModel.remoteUser.value}")
        Log.d(TAG, "print: local ${viewModel.localUsers.value}")
    }
}