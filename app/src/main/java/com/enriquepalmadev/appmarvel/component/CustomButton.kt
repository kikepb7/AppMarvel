package com.enriquepalmadev.appmarvel.component

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER
import androidx.appcompat.widget.AppCompatButton
import com.enriquepalmadev.appmarvel.R


class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {


    private var buttonName: String? = null

    init {

        // Obtain name of  buttonName
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
        buttonName = typedArray.getString(R.styleable.CustomButton_buttonName)
        typedArray.recycle()

        // Initialice button style.
        setTextColor(resources.getColor(android.R.color.white))
        setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
        gravity = CENTER
    }
}