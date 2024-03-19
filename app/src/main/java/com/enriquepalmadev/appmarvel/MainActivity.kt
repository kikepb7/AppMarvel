package com.enriquepalmadev.appmarvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.enriquepalmadev.appmarvel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Binding para acceder a los objetos de la vista del activity_main.xml
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val activityMainView = binding.root
        setContentView(activityMainView)

        // Listener de los botones
        binding.buttonComics.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Botón comics", Toast.LENGTH_SHORT).show()
        })
        binding.buttonCharacters.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Botón personajes", Toast.LENGTH_SHORT).show()
        })
        binding.buttonFilmsAndSeries.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Botón películas y series", Toast.LENGTH_SHORT).show()
        })
    }
}