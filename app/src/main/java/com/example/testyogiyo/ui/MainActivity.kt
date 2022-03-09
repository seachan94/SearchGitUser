package com.example.testyogiyo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    val searchText = MutableStateFlow("")

    private val apiFragment by lazy { ApiFragment.newInstance() }
    private val localFragment by lazy{ LocalFragment.newInstance() }

    //어떠한 Tab (fragment)가 attach 되어 있는지 확인 한다.
    private var attachFragmentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply{
            activity = this@MainActivity
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
        searchText.debounce(500L).collectLatest {
            if(it.isNotEmpty()){
                when(attachFragmentPosition){
                    0-> apiFragment.findUserFromRemote(it)
                    1->localFragment.findUserFromLocal(it)
                }
            }
        }
    }

}