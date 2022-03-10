package com.example.testyogiyo.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.testyogiyo.R
import com.example.testyogiyo.data.meta.ResultState

@BindingAdapter("items")
fun <T> RecyclerView.setItems(resultState: ResultState<Any>?) {
    if (resultState is ResultState.Success) {
        (adapter as? ListAdapter<T, *>)?.submitList((resultState.data as MutableList<T>?)?.toMutableList())
    }
}

@BindingAdapter("setItems")
fun <T> RecyclerView.setItems( data : LiveData<List<T>>){
    (adapter as? ListAdapter<T, *>)?.submitList(data.value)
}


fun getGlideRequestOption(imageName: String) =
    RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(imageName))
        .override(1024, 2048)

@BindingAdapter("imgUrl")
fun ImageView.loadThumbnail(thumbnail: String?) {
    if (thumbnail == null) {
        return
    }

    Glide.with(context).load(thumbnail)
        .error(R.drawable.ic_launcher_foreground)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(getGlideRequestOption(thumbnail))
        .transform(FitCenter(), RoundedCorners(30))
        .into(this)
}
