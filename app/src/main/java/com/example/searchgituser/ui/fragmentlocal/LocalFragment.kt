package com.example.searchgituser.ui.fragmentlocal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.searchgituser.databinding.FragmentLocalBinding
import com.example.searchgituser.ui.MainViewModel
import com.example.searchgituser.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalFragment : Fragment() {

    private lateinit var binding : FragmentLocalBinding
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
            vm = activityViewModel
        }
        adapter.onClickLikeBtn = {
            activityViewModel.deleteUserFromLocal(it)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = LocalFragment()
    }
}