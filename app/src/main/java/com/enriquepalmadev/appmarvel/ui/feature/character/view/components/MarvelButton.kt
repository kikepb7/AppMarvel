package com.enriquepalmadev.appmarvel.ui.feature.character.view.components

import android.content.Context
import android.util.AttributeSet
import com.enriquepalmadev.appmarvel.R
//Añadir icono
class MarvelButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatButton(context, attrs) {//Que extienda de RecyclerView o LinearLayout horizontal

    /*private val colorsArray = arrayOf(
        R.color.light_red,
        R.color.purple,
        R.color.orange
    )

    private var currentColor = 0

     */

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
        /*val marvelButton = findViewById<MarvelButton>(R.id.buttonCharacters)
        marvelButton.setOnClickListener {
            //changeBackgroundColor()
            println("Has hecho click en el boton customizable")
        }*/


    }

   /* private fun changeBackgroundColor(){
        // We get the color from the array
        val color = colorsArray[currentColor]

        setBackgroundColor(color)

        // Incrects the number and resets when it reaches the top
        currentColor = (currentColor + 1) % colorsArray.size
    }

    */
}


