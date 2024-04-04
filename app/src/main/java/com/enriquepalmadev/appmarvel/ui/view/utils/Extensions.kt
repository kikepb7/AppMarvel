package com.enriquepalmadev.appmarvel.ui.view.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import java.io.InputStream
import java.nio.charset.Charset

// Util file to get data of JSON
fun getJsonFromAssets(context: Context, file: String): String? {
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
    Glide.with(this)
        .load(image)
        .apply(
            RequestOptions()
                .error(R.drawable.error_404)
        )
        .placeholder(R.drawable.loading)
        .into(this)
}

// We can use this function at the same way that if we write it in the Adapter (FilmsSeriesAdapter), by this way
// we can use it more than one time
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachRoot)

