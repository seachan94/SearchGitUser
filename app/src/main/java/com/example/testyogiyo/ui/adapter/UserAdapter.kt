package com.example.testyogiyo.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.databinding.ListItemBinding
import javax.inject.Inject

class UserAdapter @Inject constructor():
    ListAdapter<UserInfo, UserAdapter.SearchListHolder>(DiffCallback){
    var onClickLikeBtn: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListHolder {
        return SearchListHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it,position)
        }
    }

    inner class SearchListHolder(private val binding : ListItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(user : UserInfo, position : Int){
                binding.apply{
                    this.userInfo = user

                    this.itemLike.setOnClickListener {
                        onClickLikeBtn?.invoke(position)
                    }
                }
            }

        }

    object DiffCallback : DiffUtil.ItemCallback<UserInfo>(){
        override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
            oldItem == newItem

    }


}