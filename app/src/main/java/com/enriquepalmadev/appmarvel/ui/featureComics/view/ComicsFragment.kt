package com.enriquepalmadev.appmarvel.ui.featureComics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicsBinding
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.featureComics.view.ComicDetailFragment.Companion.KEY_ID
import com.enriquepalmadev.appmarvel.ui.featureComics.view.adapter.ComicsAdapter
import com.enriquepalmadev.appmarvel.ui.featureComics.viewmodel.ComicsViewModel
import com.enriquepalmadev.appmarvel.ui.featureComics.viewmodel.State
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ComicsFragment : Fragment() {
    private lateinit var binding: FragmentComicsBinding
    private val viewModel: ComicsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComicsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializes the observer in case the screen state changes
        initObserver()

        // Call the viewModel to bring us the list of comics
        viewModel.getComicsList()

        initChips()
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when (state) {
                State.Error -> { showErrorMessage() }
                is State.ListReceived -> state.listComicModels?.let {
                    initRecyclerView(it)
                }
                State.Loading -> {}
                is State.NavigateToDetail -> navigateToComicDetail(state.comicId)
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    // Navigate to detail through the comic ID
    private fun navigateToComicDetail(comicId: Int) {
        val bundle = Bundle().apply {
            putInt(KEY_ID, comicId)
        }
        binding.apply {
            rvComics.findNavController().navigate(R.id.action_comicsFragment_to_comicDetail, bundle)
        }
    }

    //
    private fun initRecyclerView(list: List<ComicModel>) {
        binding.rvComics.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter = ComicsAdapter(list) { comic ->
                viewModel.onItemSelected(comic.id)
            }
        }

        binding.rvFavoriteComics.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter = ComicsAdapter(list) { comic ->
                viewModel.onItemSelected(comic.id)
            }
        }
    }

    // Error message
    private fun showErrorMessage() {
        Snackbar.make(
            binding.comicsViewContainer,
            "Ha ocurrido un error",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    // Filter Chips
    private fun initChips() {
        val chipItems = listOf("Spiderman", "Ironman", "Favoritos")

        chipItems.map {
            val chip = Chip(requireContext())

            chip.apply {
                text = it
                chip.isClickable = true
                chip.isCheckable = true
                chip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        Toast.makeText(
                            requireContext(),
                            "Seleccionado ${chip.text}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                binding.chipGroupFilter.addView(chip)
            }
        }
    }
}