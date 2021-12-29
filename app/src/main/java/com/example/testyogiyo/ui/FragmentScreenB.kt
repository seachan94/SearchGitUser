package com.example.testyogiyo.ui

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.testyogiyo.R
import com.example.testyogiyo.databinding.FragmentScreenABinding
import com.example.testyogiyo.databinding.FragmentScreenBBinding
import com.example.testyogiyo.ui.adapter.UserAdapter
import com.example.testyogiyo.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentScreenB : Fragment() {

    lateinit var binding : FragmentScreenBBinding
    private val mainViewModel : MainViewModel by activityViewModels()


    @Inject
    lateinit var userAdapter : UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentScreenBBinding.inflate(inflater,container,false).apply{
            vm = mainViewModel
            lifecycleOwner = this@FragmentScreenB
        }
        lifecycleScope.launch {
            mainViewModel.getAllUserFromDB()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wordRecyclerview.adapter = userAdapter
        userAdapter.onClickLikeBtn = {
            val isLike = !mainViewModel.localUserData.value?.get(it)?.isLike!!
            val changeUser = mainViewModel.localUserData.value?.get(it)
            changeUser!!.isLike = isLike
            mainViewModel.toggleUserDataLike(it,isLike,true)
            changeUser
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =FragmentScreenB()
    }
}