package com.enriquepalmadev.appmarvel.view.utils

import android.widget.Button
import androidx.navigation.findNavController

fun Button.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}