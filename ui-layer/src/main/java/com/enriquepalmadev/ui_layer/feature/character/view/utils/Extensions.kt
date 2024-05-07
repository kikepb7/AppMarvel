package com.enriquepalmadev.ui_layer.feature.character.view.utils

import android.widget.ImageView
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