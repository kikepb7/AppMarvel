package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentCharactersBinding

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

        //Configuramos el adaptador del recyclerView
        //adapter = CharactersAdapter()
        //recyclerView.adapter = adapter

        //Glide
        /*Glide.with(this)
            .load("https://static.wikia.nocookie.net/disney/images/f/fa/Captain-America-AOU-Render.png/revision/latest?cb=20180420015558&path-prefix=es")
            .apply(
                RequestOptions().fallback(R.drawable.capitan_america)
                    .error(R.drawable.error_404))
            .into(binding.ivEjemplo)*/

        //Coil
        binding.ivEjemplo.load("https://static.wikia.nocookie.net/disney/images/f/fa/Captain-America-AOU-Render.png/revision/latest?cb=20180420015558&path-prefix=es") {
            crossfade(true)
            placeholder(R.drawable.cargando)
            transformations(CircleCropTransformation())
        }


        return view
    }
}