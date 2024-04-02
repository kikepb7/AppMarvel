package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicsBinding
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.model.ComicProvider
import com.enriquepalmadev.appmarvel.view.adapter.ComicsAdapter

class ComicsFragment : Fragment() {
    private lateinit var binding: FragmentComicsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(binding.rvComics)
    }

    private fun initRecyclerView(view: View) {
        val manager = LinearLayoutManager(view.context)

        binding.rvComics.apply {
            layoutManager = manager
            adapter = ComicsAdapter(ComicProvider.comicsList) { comic -> onItemSelected(comic) }
        }
    }

    private fun onItemSelected(comic: Comic) {
        val bundle = Bundle().apply {
            putString("comic", comic.id.toString())
        }
        binding.apply {
            rvComics.findNavController().navigate(R.id.action_comicsFragment_to_comicDetail, bundle)
        }
    }
}