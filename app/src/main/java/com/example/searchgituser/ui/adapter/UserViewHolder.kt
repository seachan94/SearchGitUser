package com.example.searchgituser.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.searchgituser.data.remote.response.User
import com.example.searchgituser.databinding.ListItemBinding

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