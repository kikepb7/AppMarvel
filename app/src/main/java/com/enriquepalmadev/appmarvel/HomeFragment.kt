package com.enriquepalmadev.appmarvel

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.enriquepalmadev.appmarvel.databinding.ActivityMainBinding
import com.enriquepalmadev.appmarvel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    private val viewModel: HomeViewModel by viewModels()
    //Binding para acceder a los objetos de la vista del activity_main.xml
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Listener de los botones
        binding.buttonComics.setOnClickListener{
            Toast.makeText(context, "Botón comics", Toast.LENGTH_SHORT).show()
        }
        binding.buttonCharacters.setOnClickListener{
            Toast.makeText(context, "Botón personajes", Toast.LENGTH_SHORT).show()
        }
        binding.buttonFilmsAndSeries.setOnClickListener{
            Toast.makeText(context, "Botón películas y series", Toast.LENGTH_SHORT).show()
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}