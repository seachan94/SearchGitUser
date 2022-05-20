package com.example.searchgituser.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.searchgituser.data.remote.response.User
import com.example.searchgituser.databinding.ListItemBinding
import javax.inject.Inject


class UserAdapter @Inject constructor(
): PagingDataAdapter<User, UserViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  UserViewHolder(binding)
    }

    var onClickLikeBtn : ((User)->Unit)? = null

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onClickLikeBtn)
        }
    }



    object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.login == newItem.login

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

}