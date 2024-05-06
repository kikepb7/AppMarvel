package com.enriquepalmadev.appmarvel.ui.feature.comics.view.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
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
        //.placeholder(R.drawable.progress_animation) // TODO --> Adapt loader
        .into(this)
}

fun Button.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}

fun ImageButton.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.gone() {
    this.visibility = GONE
}