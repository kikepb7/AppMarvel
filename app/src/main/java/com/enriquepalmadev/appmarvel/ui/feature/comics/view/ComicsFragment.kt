package com.enriquepalmadev.appmarvel.ui.feature.comics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
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

        filterComicListener()
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when (state) {
                is State.Error -> {
                    when (state.error) {
                        is ApiError -> {
                            manageErrorState(code = state.error.code.toString())
                        }

                        UnknownHostError -> manageErrorState(code = "400")
                        Unauthorized -> manageErrorState(code = "401")
                    }
                    returnToHome()
                    manageLoadingState(show = false)
                    manageComicList(false)
                }

                is State.Exception -> {
                    manageErrorState(code = state.message)
                    returnToHome()
                    manageLoadingState(false)
                    manageComicList(false)
                }

                is State.ListReceived -> {
                    manageLoadingState(false)
                    manageComicList(true)
                    returnToHome()
                    state.listComicModels?.let {
                        initRecyclerView(it)
                    }
                }

                State.Loading -> {
                    manageLoadingState(show = true)
                    manageComicList(false)
                }

                is State.NavigateToDetail -> navigateToComicDetail(comicId = state.comicId)

                is State.FilteredListByName -> {
                    manageLoadingState(false)
                    manageComicList(true)
                    state.filteredComicList?.let { initRecyclerView(it) }
                }
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

    // RecyclerView configuration
    private fun setupRecyclerView(recyclerView: RecyclerView, list: List<ComicModel>) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter = ComicsAdapter(list) { comic ->
                viewModel.onItemSelected(comic)
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

    private fun manageLoadingState(show: Boolean) {
        binding.apply {
            if (show) listProgressBar.visibility = VISIBLE else listProgressBar.visibility = GONE
        }
    }

    private fun manageComicList(show: Boolean) {
        binding.apply {
            if (show) {
                rvComics.visibility = VISIBLE
                rvFavoriteComics.visibility = VISIBLE
                tvComicList.visibility = VISIBLE
                ivLogoAllComics.visibility = VISIBLE
                tvFavoriteComics.visibility = VISIBLE
                ivLogoFavoriteComics.visibility = VISIBLE
            } else {
                rvComics.visibility = GONE
                rvFavoriteComics.visibility = GONE
                tvComicList.visibility = GONE
                ivLogoAllComics.visibility = GONE
                tvFavoriteComics.visibility = GONE
                ivLogoFavoriteComics.visibility = GONE
            }
        }
    }

    private fun manageErrorState(code: String) {
        binding.apply {
            ivError.visibility = VISIBLE
            tvErrorCode.visibility = VISIBLE
            tvErrorCode.text = getString(R.string.error_code, code)
        }
    }
}