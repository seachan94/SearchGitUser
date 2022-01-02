package com.example.testyogiyo.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.testyogiyo.data.UserInfo
import com.google.android.material.tabs.TabLayout

@BindingAdapter("items")
fun <T> RecyclerView.setItems(networkStatus: NetworkStatus<Any>) {

    if (networkStatus is NetworkStatus.Success) {
        (adapter as? ListAdapter<T, *>)?.submitList(networkStatus.data as MutableList<T>?)
    }
}

@BindingAdapter("localitems")
fun <T> RecyclerView.setItems(localUserData: LiveData<ArrayList<UserInfo>>) {
    (adapter as? ListAdapter<T, *>)?.submitList(localUserData.value as MutableList<T>?)

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

@BindingAdapter(
    value = ["setFragment" , "supportFragment"],
    requireAll = true
)
fun setFragmentInTabLayout( view : View, fragment : Fragment, supportFragment : FragmentManager?){
    val transaction = supportFragment?.beginTransaction()
    transaction?.replace(view.id,fragment)
    transaction?.commit()
}