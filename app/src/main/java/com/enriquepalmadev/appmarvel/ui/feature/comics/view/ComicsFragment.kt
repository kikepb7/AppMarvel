package com.enriquepalmadev.appmarvel.ui.feature.comics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Unauthorized
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.UnknownHostError
import com.enriquepalmadev.appmarvel.databinding.FragmentComicsBinding
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.view.adapter.ComicsAdapter
import com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel.ComicsViewModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel.State
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.enriquepalmadev.appmarvel.ui.feature.comics.view.utils.navigateTo

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

        filterComicListener()
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when (state) {
                is State.Error -> {
                    when (state.error) {
                        is ApiError -> {
                            manageErrorApi(code = state.error.code.toString())
                            showErrorMessage(code = state.error.code, message = state.error.message)
                        }
                        UnknownHostError -> manageErrorApi(code = "400")
                        Unauthorized -> manageErrorApi(code = "401")
                    }
                    returnToHome()
                    manageLoadingView(false)
                }

                is State.Exception -> {
                    manageErrorApi(code = state.message)
                    returnToHome()
                    manageLoadingView(false)
                }

                is State.ListReceived -> {
                    manageLoadingView(false)
                    returnToHome()
                    state.listComicModels?.let {
                        initRecyclerView(it)
                    }
                }

                State.Loading -> {
                    manageLoadingView(true)
                }
                is State.NavigateToDetail -> navigateToComicDetail(comicId = state.comicId)

                is State.FilteredListByName -> {
                    manageLoadingView(false)
                    state.filteredComicList?.let { initRecyclerView(it) }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun returnToHome() {
        binding.ibBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        //binding.ibBack.navigateTo(R.id.homeFragment)
    }

    // Navigate to detail through the comic ID with safeArgs
    private fun navigateToComicDetail(comicId: Int) {
        val action = ComicsFragmentDirections.actionComicsFragmentToComicDetail(id = comicId)
        binding.apply {
            rvComics.findNavController().navigate(action)
        }
    }

    // RecyclerView configuration
    private fun setupRecyclerView(recyclerView: RecyclerView, list: List<ComicModel>) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter = ComicsAdapter(list) { comic ->
                viewModel.onItemSelected(comic.id)
            }
        }
    }

    // Update RecyclerView
    private fun updateRecyclerView(recyclerView: RecyclerView, list: List<ComicModel>) {
        (recyclerView.adapter as? ComicsAdapter)?.updateComics(list)
    }

    // Initialize both RecyclerViews
    private fun initRecyclerView(list: List<ComicModel>) {
        if (binding.rvComics.adapter == null) {
            setupRecyclerView(binding.rvComics, list)
        } else {
            updateRecyclerView(binding.rvComics, list = list)
        }
        setupRecyclerView(binding.rvFavoriteComics, list)
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
    private fun manageErrorApi(code: String) {
        binding.apply {
            ivError.isVisible
            tvErrorCode.isVisible
            tvErrorCode.text = "Error ${code}"
            rvComics.isGone
            rvFavoriteComics.isGone
            tvComicList.isGone
            tvFavoriteComics.isGone
            ivLogoAllComics.isGone
            ivLogoFavoriteComics.isGone
        }
    }

    // Manage Loading State view
    private fun manageLoadingView(show: Boolean) {
        if (show) {
            binding.apply {
                listProgressBar.isVisible
                tvComicList.isGone
                tvFavoriteComics.isGone
                ivLogoAllComics.isGone
                ivLogoFavoriteComics.isGone
            }
        }
        else {
            binding.apply {
                listProgressBar.isGone
                tvComicList.isVisible
                tvFavoriteComics.isVisible
                ivLogoAllComics.isVisible
                ivLogoFavoriteComics.isVisible
            }
        }
    }
}