package com.example.testyogiyo.ui.fragmentapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testyogiyo.databinding.FragmentApiBinding
import com.example.testyogiyo.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private lateinit var binding :FragmentApiBinding
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
                true -> viewModel.deleteUserFromLocal(it.login)
                false -> viewModel.insertUserToLocal(it)
            }
        }

        return binding.root
    }

    fun findUserFromRemote(searchText : String){
        viewModel.findUserFromRemoteRepository(searchText)
    }

    companion object {
       @JvmStatic
        fun newInstance()=ApiFragment()
    }

}