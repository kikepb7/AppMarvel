package com.enriquepalmadev.appmarvel.ui.feature.character.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Binding para acceder a los objetos de la vista del activity_main.xml
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var currentColorIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el color actual guardado en SharedPreferences
        val currentColor = getSavedColor()

        // Aplicar el color al botón de personajes
        binding.buttonCharacters.setBackgroundResource(currentColor)

        // Button Listener
        binding.apply {
            // Navigate through the path indicated in "main_graph.xml"
            buttonComics.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_comicsFragment)
                Toast.makeText(context, "Botón comics", Toast.LENGTH_SHORT).show()
            }

            buttonCharacters.setOnClickListener {
                val randomColor = getRandomColor()
                buttonCharacters.setBackgroundResource(randomColor)
                saveColor(randomColor)
                findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
                Toast.makeText(context, "Botón personajes", Toast.LENGTH_SHORT).show()
            }

            buttonFilmsAndSeries.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_filmsAndSeriesFragment)
                Toast.makeText(context, "Botón películas y series", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveColor(color: Int) {
        sharedPreferences.edit().putInt("buttonColor", color).apply()
    }

    private fun getSavedColor(): Int {
        return sharedPreferences.getInt("buttonColor", R.color.light_red)
    }

    private fun getRandomColor(): Int {
        // Obtener el color actual del array de colores
        val colorsArray = arrayOf(
            R.color.purple,
            R.color.orange,
            R.color.light_red
        )

        // Obtener el color actual utilizando el índice actual
        val colorId = colorsArray[currentColorIndex]

        // Avanzar al siguiente color
        currentColorIndex = (currentColorIndex + 1) % colorsArray.size

        // Devolver el ID del color
        return colorId
    }
}