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
import androidx.paging.map
import com.example.testyogiyo.databinding.FragmentApiBinding
import com.example.testyogiyo.ui.MainActivity
import com.example.testyogiyo.ui.MainViewModel
import com.example.testyogiyo.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private lateinit var binding: FragmentApiBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    private val adapter by lazy { UserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentApiBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            wordRecyclerview.adapter = adapter
            vm = activityViewModel
        }

        adapter.onClickLikeBtn = {
            when (it.isLike) {
                true -> activityViewModel.deleteUserFromLocal(it)
                false -> activityViewModel.insertUserToLocal(it)
            }
        }
        observeData()
        return binding.root
    }

    fun observeData() = activityViewModel.resultState.observe(viewLifecycleOwner) {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.submitData(it)
            binding.wordRecyclerview.scrollToPosition(0)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApiFragment()
    }

}