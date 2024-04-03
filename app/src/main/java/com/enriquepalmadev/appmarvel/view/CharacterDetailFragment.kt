package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enriquepalmadev.appmarvel.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {

    companion object{
        const val KEY_ID = "id"
    }
    private lateinit var  binding: FragmentCharacterDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding.root
    }
}