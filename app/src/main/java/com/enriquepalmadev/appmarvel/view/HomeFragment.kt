package com.enriquepalmadev.appmarvel.view

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Listener de los botones
        binding.buttonComics.setOnClickListener {
            // Navega a la ruta indicada en main_graph.xml
            findNavController().navigate(R.id.action_homeFragment_to_comicsFragment)
            Toast.makeText(context, "Botón comics", Toast.LENGTH_SHORT).show()
        }
        binding.buttonCharacters.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
            Toast.makeText(context, "Botón personajes", Toast.LENGTH_SHORT).show()
        }
        binding.buttonFilmsAndSeries.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_filmsAndSeriesFragment)
            Toast.makeText(context, "Botón películas y series", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}