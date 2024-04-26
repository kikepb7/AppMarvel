package com.enriquepalmadev.appmarvel.ui.components

import android.content.Context
import android.util.AttributeSet
import com.enriquepalmadev.appmarvel.R

class MarvelButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatButton(context, attrs) {//Que extienda de RecyclerView o LinearLayout horizontal
    init{
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarvelButton)
        val buttonText = typedArray.getString(R.styleable.MarvelButton_buttonText)
        val foregroundButton = typedArray.getDrawable(R.styleable.MarvelButton_foregroundButton)

        // Setting my view
        text = buttonText
        background = foregroundButton
        typedArray.recycle()

        this.setOnClickListener {
            //changeBackgroundColor()
            print("Has hecho click en el boton customizable")
        }
    }
}


