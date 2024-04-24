package com.enriquepalmadev.appmarvel.ui.view.extensions

import android.widget.Button
import android.widget.ImageView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R

fun ImageView.loadImage(image: String) {
    Glide.with(this)
        .load(image)
        .apply(
            RequestOptions()
                .error(R.drawable.error_404)
        )
        .placeholder(R.drawable.loading)
        .into(this)
}

fun Button.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}

