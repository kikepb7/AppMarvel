package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enriquepalmadev.appmarvel.databinding.FragmentComicDetailBinding

class ComicDetail : Fragment() {

    private lateinit var binding: FragmentComicDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicDetailBinding.inflate(layoutInflater)
        return binding.root
    }
}