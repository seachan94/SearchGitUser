package com.example.testyogiyo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.testyogiyo.R
import com.example.testyogiyo.data.remote.response.User
import com.example.testyogiyo.databinding.ActivityMainBinding
import com.example.testyogiyo.ui.fragmentapi.ApiFragment
import com.example.testyogiyo.ui.fragmentlocal.LocalFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    private val apiFragment by lazy { ApiFragment.newInstance() }
    private val localFragment by lazy{ LocalFragment.newInstance() }

    //어떠한 Tab (fragment)가 attach 되어 있는지 확인 한다.
    private var attachFragmentPosition = 0
    var test = 'a'
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply{
            vm = viewModel
            lifecycleOwner = this@MainActivity
            searchBtn.setOnClickListener{
                //clickSearchBtn(attachFragmentPosition)
                viewModel.insertUserToLocal(User(
                    (test++).toString(),(test++).toString()
                ))
                viewModel.a()
            }
        }
        setContentView(binding.root)
        fragmentLayout()
        testObserve()
    }
    private fun testObserve(){
        lifecycleScope.launchWhenStarted {
            viewModel.testlocallUser.collect {
                Log.d("sechan", "testObserve1: $it")
            }
            viewModel.test2.collectLatest {
                Log.d("sechan", "testObserve: $it")
            }
        }
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

    private fun clickSearchBtn(position: Int){
        when(position){
            0->{
                viewModel.getUserFromRemote(viewModel.searchText.value)
            }
            1->{
                viewModel.getUserFromLocal(viewModel.searchText.value)
            }
        }
    }
}