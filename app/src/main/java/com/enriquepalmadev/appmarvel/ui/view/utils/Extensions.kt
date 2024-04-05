package com.enriquepalmadev.appmarvel.ui.view.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import java.io.InputStream
import java.nio.charset.Charset


fun getJsonFromAssets(context: Context, file: String): String?{
    var json = ""
    val stream: InputStream = context.assets.open(file)
    val size: Int = stream.available()
    val buffer = ByteArray(size)
    stream.read(buffer)
    stream.close()

    json = String(buffer, Charset.defaultCharset())
    return json
}

fun ImageView.loadImage(image: String) {
    /*load(image) {
        crossfade(true)
        size(800, 800)
        scale(Scale.FILL)
        placeholder(R.drawable.cargando)
    }*/

    Glide.with(this)
        .load(image)
        .apply(
            RequestOptions().fallback(R.drawable.capitan_america)
                .error(R.drawable.error_404)
                .placeholder(R.drawable.cargando)
        )
        .into(this)
}