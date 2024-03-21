package com.enriquepalmadev.appmarvel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.appmarvel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Binding to get the view components of fragment_home.xml
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Listener de los botones
        binding.apply {
            buttonComics.setOnClickListener {
                // Navega a la ruta indicada en main_graph.xml
                findNavController().navigate(R.id.action_homeFragment_to_comicsFragment)
                Toast.makeText(context, "Botón comics", Toast.LENGTH_SHORT).show()
            }
            buttonCharacters.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
                Toast.makeText(context, "Botón personajes", Toast.LENGTH_SHORT).show()
            }
            buttonFilmsAndSeries.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_filmsAndSeriesFragment)
                Toast.makeText(context, "Botón películas y series", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}