package com.enriquepalmadev.appmarvel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Convertimos el diseño en XML en una vista de Android
        val fragment = inflater.inflate(R.layout.fragment_home, container, false)

        // Referencias de los botones (por ID) desde fragment_home.xml
        val btnComics = fragment.findViewById<Button>(R.id.button_comics)
        val btnSeries = fragment.findViewById<Button>(R.id.button_films_and_series)
        val btnCharacters = fragment.findViewById<Button>(R.id.button_characters)

        // Eventos de los botones
        btnComics.setOnClickListener {
            // Navega a la ruta indicada en main_graph.xml
            findNavController().navigate(R.id.action_homeFragment_to_comicsFragment)
        }

        btnSeries.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_seriesFragment)
        }

        btnCharacters.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
        }

        return fragment
    }
}