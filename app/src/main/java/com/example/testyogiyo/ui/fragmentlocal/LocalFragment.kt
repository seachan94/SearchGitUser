package com.example.testyogiyo.ui.fragmentlocal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.testyogiyo.databinding.FragmentLocalBinding
import com.example.testyogiyo.ui.MainViewModel
import com.example.testyogiyo.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalFragment : Fragment() {

    private lateinit var binding : FragmentLocalBinding
    private val viewModel : LocalViewModel by viewModels()
    private val activityViewModel : MainViewModel by activityViewModels()

    @Inject
    lateinit var adapter : UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocalBinding.inflate(inflater,container,false).apply{
            userRecyclerview.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        adapter.onClickLikeBtn = {
            when(it.isLike){
                true -> activityViewModel.deleteUserFromLocal(it.login)
                false -> activityViewModel.insertUserToLocal(it)
            }
        }
        observeLocalUserData()
        return binding.root
    }

    private fun observeLocalUserData(){
        activityViewModel.localUsers.observe(viewLifecycleOwner){
            viewModel.localUserData.value = it
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LocalFragment()
    }
}