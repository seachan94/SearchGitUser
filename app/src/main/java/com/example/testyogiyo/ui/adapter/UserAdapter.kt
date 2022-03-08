package com.example.testyogiyo.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testyogiyo.data.remote.response.User
import com.example.testyogiyo.databinding.ListItemBinding
import javax.inject.Inject

class UserAdapter @Inject constructor():
    ListAdapter<User, UserViewHolder>(DiffCallback){
    var onClickLikeBtn: ((Int) -> User)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        return UserViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it,position)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.login == newItem.login

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem==newItem
    }

}