package com.enriquepalmadev.appmarvel

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.enriquepalmadev.appmarvel.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    private val viewModel: HomeViewModel by viewModels()
    //Binding para acceder a los objetos de la vista del activity_main.xml
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Listener de los botones
        binding.buttonComics.setOnClickListener{
            // Navega a la ruta indicada en main_graph.xml
            findNavController().navigate(R.id.action_homeFragment_to_comicsFragment)
            Toast.makeText(context, "Botón comics", Toast.LENGTH_SHORT).show()
        }
        binding.buttonCharacters.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_seriesFragment)
            Toast.makeText(context, "Botón personajes", Toast.LENGTH_SHORT).show()
        }
        binding.buttonFilmsAndSeries.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
            Toast.makeText(context, "Botón películas y series", Toast.LENGTH_SHORT).show()
        }
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}