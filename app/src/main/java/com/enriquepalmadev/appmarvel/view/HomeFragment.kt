package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.enriquepalmadev.appmarvel.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonComics.setOnClickListener { replaceFragment(ComicsFragment()) }
        binding.buttonCharacters.setOnClickListener { replaceFragment(CharactersFragment()) }
        binding.buttonFilmsAndSeries.setOnClickListener { replaceFragment(FilmsSeriesFragment()) }
    }


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.buttonComics.setOnClickListener { replaceFragment(ComicsFragment()) }
        binding.buttonCharacters.setOnClickListener { replaceFragment(CharactersFragment()) }
        binding.buttonFilmsAndSeries.setOnClickListener { replaceFragment(FilmsSeriesFragment()) }
    }*/

    fun replaceFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

}