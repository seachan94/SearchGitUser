package com.example.testyogiyo.ui.fragmentapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testyogiyo.databinding.FragmentApiBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private lateinit var binding :FragmentApiBinding
    private val viewModel : ApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApiBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun findUserFromRemote(searchText : String){
        Log.d("sechan", "test: $searchText")
        viewModel.findUserFromRemoteRepository(searchText)
    }
    companion object {
       @JvmStatic
        fun newInstance()=ApiFragment()
    }

}