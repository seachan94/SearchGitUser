package com.example.testyogiyo.ui.fragmentlocal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testyogiyo.databinding.FragmentLocalBinding
import com.example.testyogiyo.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalFragment : Fragment() {

    private lateinit var binding : FragmentLocalBinding
    private val viewModel : LocalViewModel by viewModels()

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
                true -> viewModel.deleteUserFromLocal(it.login)
                false -> viewModel.insertUserToLocal(it)
            }
        }
        return binding.root
    }

    fun findUserFromLocal(searchId : String){
        viewModel.getUserFromLocal(searchId)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LocalFragment()
    }
}