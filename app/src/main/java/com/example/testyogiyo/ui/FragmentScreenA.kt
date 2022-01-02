package com.example.testyogiyo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.testyogiyo.databinding.FragmentScreenABinding
import com.example.testyogiyo.ui.adapter.UserAdapter
import com.example.testyogiyo.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentScreenA : Fragment() {

    lateinit var binding : FragmentScreenABinding
    private val mainViewModel : MainViewModel by activityViewModels()

    @Inject
    lateinit var userAdapter : UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentScreenABinding.inflate(inflater,container,false).apply{
            vm = mainViewModel
            lifecycleOwner = this@FragmentScreenA
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wordRecyclerview.adapter = userAdapter

        userAdapter.onClickLikeBtn = {
            val isLike = !mainViewModel.userData.value?.get(it)?.isLike!!
            val changeUser = mainViewModel.userData.value?.get(it)
            changeUser!!.isLike = isLike

            mainViewModel.toggleUserDataLike(it,isLike,false)
            changeUser
        }

    }

    companion object {
       @JvmStatic
        fun newInstance()=FragmentScreenA()
    }

}