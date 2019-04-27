package com.darja.tmdb.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

@BindingAdapter("url", "baseUrl", requireAll = false)
fun ImageView.setImageUrl(url: String?, baseUrl: String?) {
    val imageUrl = if (baseUrl.isNullOrEmpty()) url else baseUrl + url

    val crossFade = DrawableCrossFadeFactory.Builder(200)
        .setCrossFadeEnabled(true)
        .build()

    Glide.with(context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade(crossFade))
        .into(this)
}
