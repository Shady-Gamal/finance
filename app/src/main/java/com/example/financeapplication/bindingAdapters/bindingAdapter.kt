package com.example.financeapplication.bindingAdapters

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.financeapplication.R
import java.util.Date

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.user_profile_image)
            .into(view)
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

@BindingAdapter("showDate")

fun showDate(view : TextView, dateValue : Long?){
    if (dateValue != null) {
        val date = Date(dateValue)
        val format = SimpleDateFormat("dd MMM yyyy")
        view.text= format.format(date)
    }

}