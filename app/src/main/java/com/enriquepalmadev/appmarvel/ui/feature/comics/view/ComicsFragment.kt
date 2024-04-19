package com.enriquepalmadev.appmarvel.ui.feature.comics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Unauthorized
import com.enriquepalmadev.appmarvel.databinding.FragmentComicsBinding
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.view.adapter.ComicsAdapter
import com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel.ComicsViewModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel.State
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

        //initChips()

        filterComicListener()
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when (state) {
                is State.Error -> {
                    when (state.error) {
                        is ApiError -> {
                            returnToHome()
                            manageErrorApi()
                            showErrorMessage(code = state.error.code, message = state.error.message)
                        }
                        Unauthorized -> {}
                    }
                }

                is State.ListReceived -> {
                    returnToHome()
                    state.listComicModels?.let {
                        initRecyclerView(it)
                    }
                }

                State.Loading -> {}
                is State.NavigateToDetail -> navigateToComicDetail(state.comicId)
                //State.EmptyList -> {}
                is State.FilteredListByName -> state.filteredComicList?.let { initRecyclerView(it) }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun returnToHome() {
        binding.ibBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    // Navigate to detail through the comic ID with safeArgs
    private fun navigateToComicDetail(comicId: Int) {
        val action = ComicsFragmentDirections.actionComicsFragmentToComicDetail(id = comicId)
        binding.apply {
            rvComics.findNavController().navigate(action)
        }
    }

    private fun initRecyclerView(list: List<ComicModel>) {
        if (binding.rvComics.adapter == null) {
            binding.rvComics.apply {
                layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

                adapter = ComicsAdapter(list) { comic ->
                    viewModel.onItemSelected(comic.id)
                }
            }
        } else {
            (binding.rvComics.adapter as ComicsAdapter).updateComics(list)
        }

        binding.rvFavoriteComics.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter = ComicsAdapter(list) { comic ->
                viewModel.onItemSelected(comic.id)
            }
        }
    }

    private fun filterComicListener() {
        binding.svFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(text: String): Boolean {
                viewModel.filterByName(text)

                return false
            }
        })
    }

    // Error message
    private fun showErrorMessage(code: Int, message: String) {
        Snackbar.make(
            binding.comicsViewContainer,
            "Código de error: $code \n$message",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    // Manage errors on fragment
    private fun manageErrorApi() {
        binding.apply {
            ivError.visibility = View.VISIBLE
            tvError.visibility = View.VISIBLE
            rvComics.visibility = View.GONE
            rvFavoriteComics.visibility = View.GONE
            tvComicList.visibility = View.GONE
            tvFavoriteComics.visibility = View.GONE
            ivLogoAllComics.visibility = View.GONE
            ivLogoFavoriteComics.visibility = View.GONE
        }
    }

    /*// Filter Chips
    private fun initChips() {
        val chipItems = listOf("Spider-man", "Ironman", "Hulk")
        var lastCheckedChip : Chip? = null

        chipItems.map {
            val chip = Chip(requireContext())

            chip.apply {
                text = it
                chip.isClickable = true
                chip.isCheckable = true
                chip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        lastCheckedChip?.isClickable = true
                        lastCheckedChip = this
                        binding.svFilter.children.forEach { view ->
                            if (view != this) {
                                (view as Chip).isClickable = false
                            }
                        }
                        filterComicsBySelectedChips()
                    } else {
                        binding.svFilter.children.forEach { view ->
                            (view as Chip).isClickable = true
                        }
                        filterComicsBySelectedChips()
                    }
                }
                binding.svFilter.addView(chip)
            }
        }
    }

    private fun filterComicsBySelectedChips() {
        val selectedChips = binding.svFilter.checkedChipIds.joinToString(",") { id ->
            binding.svFilter.findViewById<Chip>(id).text.toString()
        }

        if (selectedChips.isNotEmpty()) {
            viewModel.filterByName(selectedChips)
        } else {
            viewModel.getComicsList()
        }
    }*/
}