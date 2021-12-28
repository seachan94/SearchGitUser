package com.example.testyogiyo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        binding = ActivityMainBinding.inflate(layoutInflater).apply{
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }

        setContentView(binding.root)

        Log.d("sechan", "onCreatemain: $viewModel")
        binding.searchBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.requestUser()
            }
        }
        //test 꼭 변경 할 것
        viewModel.fragmentLayout.observe(this,{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout,it)
            transaction.commit()
        })
    }

}