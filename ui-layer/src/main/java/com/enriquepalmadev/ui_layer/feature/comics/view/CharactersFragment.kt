package com.enriquepalmadev.ui_layer.feature.comics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.ui_layer.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {
    lateinit var binding: FragmentCharactersBinding
    lateinit var recyclerView: RecyclerView

    //lateinit var adapter: CharactersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = binding.root

        //Inicializamos el recyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }
}