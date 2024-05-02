package com.enriquepalmadev.ui_layer.feature.comics.view.components

import android.content.Context
import android.util.AttributeSet
import com.enriquepalmadev.ui_layer.R

class MarvelButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatButton(context, attrs) {

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarvelButton)
        val buttonText = typedArray.getString(R.styleable.MarvelButton_buttonText)
        val foregroundButton = typedArray.getDrawable(R.styleable.MarvelButton_foregroundButton)

        // Setting my view
        text = buttonText
        background = foregroundButton
        typedArray.recycle()
    }
}
