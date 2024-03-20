package com.enriquepalmadev.appmarvel

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.google.android.material.button.MaterialButton

class ButtonHome @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    style: Int = 0
    ): MaterialButton(context, attrs, style) {

    val styleButton = context.obtainStyledAttributes(attrs, R.styleable.ButtonHome)
    val title = styleButton.getString(R.styleable.ButtonHome_buttonText)

        init {
            text = title
            gravity = Gravity.CENTER
            styleButton.recycle()
        }
}