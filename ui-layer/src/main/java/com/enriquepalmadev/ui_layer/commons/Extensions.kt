package com.enriquepalmadev.ui_layer.commons

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.ui_layer.R


fun ImageView.loadImage(image: String) {

    Glide.with(this)
        .load(image)
        .apply(
            RequestOptions().fallback(R.drawable.capitan_america)
                .error(R.drawable.error_404)
                .placeholder(R.drawable.cargando)
        )
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
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}