package com.example.testyogiyo.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.testyogiyo.data.remote.response.User
import com.example.testyogiyo.databinding.ListItemBinding

class UserViewHolder(private val binding : ListItemBinding) :
    RecyclerView.ViewHolder(binding.root){

    fun bind(user : User,clickEvent : ((User)->Unit)?){
        binding.apply{
            data = user
            itemLike.setOnClickListener {
                clickEvent?.invoke(user)
                user.isLike = !user.isLike
                data = user
            }
        }
    }

}