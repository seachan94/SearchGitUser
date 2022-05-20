package com.example.searchgituser.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.searchgituser.R
import com.example.searchgituser.databinding.ActivityMainBinding
import com.example.searchgituser.ui.fragmentapi.ApiFragment
import com.example.searchgituser.ui.fragmentlocal.LocalFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    private val apiFragment by lazy { ApiFragment.newInstance() }
    private val localFragment by lazy{ LocalFragment.newInstance() }

    //어떠한 Tab (fragment)가 attach 되어 있는지 확인 한다.
    private var attachFragmentPosition = 0
    val TAG = "sechan"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply{
            vm = viewModel
            lifecycleOwner = this@MainActivity
            searchBtn.setOnClickListener{
                clickSearchBtn(attachFragmentPosition)
          }
        }
        setContentView(binding.root)
        fragmentLayout()
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