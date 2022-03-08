package com.example.testyogiyo.ui.fragmentlocal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testyogiyo.databinding.FragmentLocalBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalFragment : Fragment() {

    private lateinit var binding : FragmentLocalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocalBinding.inflate(inflater,container,false)

        return binding.root
    }

    fun findUserFromLocal(s : String){
    }

    companion object {
        @JvmStatic
        fun newInstance() = LocalFragment()
    }
}