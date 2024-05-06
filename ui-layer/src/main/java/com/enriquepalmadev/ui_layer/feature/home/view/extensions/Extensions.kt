package com.enriquepalmadev.ui_layer.feature.home.view.extensions

import android.widget.Button
import androidx.navigation.findNavController

fun Button.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}

