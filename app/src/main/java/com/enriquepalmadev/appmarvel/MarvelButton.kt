package com.enriquepalmadev.appmarvel

import android.content.Context
import android.util.AttributeSet

class MarvelButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatButton(context, attrs) {

    var button_height = 0
    var button_width = 0

    init{
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MarvelButton)
        button_height = attributes.getInt(R.styleable.MarvelButton_android_height, 200)
        button_width = attributes.getInt(R.styleable.MarvelButton_android_width, 60)
        attributes.recycle()
    }

    /*
    // This function give us some information about the configuration of patterns views measures that hepls us to set correctly the size of our components (button in this case)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if(button_width !=0 && button_height != 0){
            setMeasuredDimension(button_width, button_height)
        }
    }
     */
}