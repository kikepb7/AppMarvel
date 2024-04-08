package com.enriquepalmadev.appmarvel.ui.view.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import com.google.android.material.snackbar.Snackbar
import java.io.InputStream
import java.nio.charset.Charset

// Util file to get data of JSON
fun Context.getJsonFromAssets(file: String): String {
    var json = ""
    val stream: InputStream = this.assets.open(file)
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

// We can use this function at the same way that if we write it in the Adapter (FilmSerieAdapter), by this way
// we can use it more than one time
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachRoot)

fun Button.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}

// Snackbar function
fun showSnackbar(view: View, msg: String){
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}

