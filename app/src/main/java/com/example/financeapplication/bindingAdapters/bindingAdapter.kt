package com.example.financeapplication.bindingAdapters

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.financeapplication.R
import com.google.android.material.progressindicator.CircularProgressIndicator

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.user_profile_image)
            .into(view)
    }




}


@BindingAdapter("bitmap")
fun loadImage(view: ImageView, bitmap: Bitmap?) {
    if (bitmap != null) {
        Glide.with(view.context)
            .load(bitmap)
            .placeholder(R.drawable.user_profile_image)
            .into(view)
    }
}