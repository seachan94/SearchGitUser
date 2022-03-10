package com.example.testyogiyo.ui.fragmentapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testyogiyo.databinding.FragmentApiBinding
import com.example.testyogiyo.ui.MainActivity
import com.example.testyogiyo.ui.MainViewModel
import com.example.testyogiyo.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private lateinit var binding :FragmentApiBinding
    private val activityViewModel : MainViewModel by activityViewModels()
    private val viewModel : ApiViewModel by viewModels()

    @Inject
    lateinit var adapter : UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApiBinding.inflate(inflater,container,false)
        binding.apply{
            lifecycleOwner = viewLifecycleOwner
            wordRecyclerview.adapter= adapter
            vm = viewModel
        }

        adapter.onClickLikeBtn = {
            when(it.isLike){
                true -> activityViewModel.deleteUserFromLocal(it.login)
                false -> activityViewModel.insertUserToLocal(it)
            }
        }

        observeRemoteUserData()
        return binding.root
    }

    private fun observeRemoteUserData(){
        activityViewModel.remoteUser.observe(viewLifecycleOwner){
            viewModel.remoteUser.value=it
        }
    }

    companion object {
       @JvmStatic
        fun newInstance()=ApiFragment()
    }

}