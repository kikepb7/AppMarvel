package com.enriquepalmadev.appmarvel.view.component

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
        // Obtener los atributos personalizados
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
        // Obtener el texto personalizado del botón
        val customText = typedArray.getString(R.styleable.CustomButton_buttonName)
        // Aplicar el texto personalizado al botón
        text = customText
        // Liberar los recursos del TypedArray
        typedArray.recycle()
    }

}