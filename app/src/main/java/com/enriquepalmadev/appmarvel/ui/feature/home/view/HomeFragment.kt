package com.enriquepalmadev.appmarvel.ui.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentHomeBinding
import com.enriquepalmadev.appmarvel.ui.feature.home.view.extensions.navigateTo

class HomeFragment : Fragment() {

    // Binding to access to view objects on activity_main.xml
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Button Listener
        binding.apply {
            // Navigate through the path indicated in "main_graph.xml"
            buttonComics.navigateTo(R.id.action_homeFragment_to_comicsFragment)
            buttonCharacters.navigateTo(R.id.action_homeFragment_to_charactersFragment)
            buttonFilmsAndSeries.navigateTo(R.id.action_homeFragment_to_filmsAndSeriesFragment)
        }
        return binding.root
    }
}