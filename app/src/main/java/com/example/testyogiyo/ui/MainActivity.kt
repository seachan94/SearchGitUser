package com.example.testyogiyo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testyogiyo.R
import com.example.testyogiyo.databinding.ActivityMainBinding
import com.example.testyogiyo.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel  by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //git test
        binding = ActivityMainBinding.inflate(layoutInflater).apply{
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }

        setContentView(binding.root)
        viewModel.searchText.observe(this){
            lifecycleScope.launch {
                if(viewModel.checkPage == 0){
                    Log.d("sechan", "onCreate: $it")
                    viewModel.requestUser()
                }
                else {
                    lifecycleScope.launch {
                        viewModel.getUserFromDB()
                    }
                }
            }
        }

        viewModel.fragmentLayout.observe(this,{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout,it)
            transaction.commit()
        })
    }

}