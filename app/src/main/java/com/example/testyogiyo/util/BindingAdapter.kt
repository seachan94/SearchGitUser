package com.example.testyogiyo.util

import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import com.example.testyogiyo.data.NetworkStatus
import com.google.android.material.tabs.TabLayout

@BindingAdapter(
    value = ["dividerHeight", "dividerPadding", "dividerColor"],
    requireAll = false
)
fun RecyclerView.setDivider(
    dividerHeight: Float?,
    dividerPadding: Float?,
    @ColorInt dividerColor: Int?
) {

    val decoration = Decoration(
        height = dividerHeight ?: 0f,
        padding = dividerPadding ?: 0f,
        color = dividerColor ?: Color.TRANSPARENT,
    )
    addItemDecoration(decoration)
}

@BindingAdapter("items")
fun <T> RecyclerView.setItems(networkStatus: NetworkStatus<Any>) {
    Log.d("sechan", "setItems: $networkStatus")
    if (networkStatus is NetworkStatus.Success) {
        Log.d("sechan", "setItems: ${networkStatus.data}")
        (adapter as? ListAdapter<T, *>)?.submitList(networkStatus.data as MutableList<T>?)
    }
}

@BindingAdapter("addOnTabSelectedListener")
fun <T> addOnTabSelectedListener(tabLayout: TabLayout, listener : TabLayout.OnTabSelectedListener) {
    tabLayout.addOnTabSelectedListener(listener)
}


fun getGlideRequestOption(imageName: String) =
    RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(imageName))
        .override(1024, 2048)

@BindingAdapter("imgUrl")
fun ImageView.loadThumbnail(thumbnail: String?) {
    Log.d("sechan", "loadThumbnail: $thumbnail")
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
