package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicsBinding
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.view.ComicDetailFragment.Companion.KEY_ID
import com.enriquepalmadev.appmarvel.view.adapter.ComicsAdapter
import com.enriquepalmadev.appmarvel.viewmodel.ComicsViewModel
import com.enriquepalmadev.appmarvel.viewmodel.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ComicsFragment : Fragment() {
    private lateinit var binding: FragmentComicsBinding
    private val viewModel: ComicsViewModel  by viewModels()

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
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when(state) {
                State.Error -> {}
                is State.ListReceived -> initRecyclerView(state.listComics)
                State.Loading -> {}
                is State.NavigateToDetail -> navigateToComicDetail(state.comicId)
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToComicDetail(comicId: Long) {
        val bundle = Bundle().apply {
            putLong(KEY_ID, comicId)
//              putSerializable("comic", comic)
        }
        binding.apply {
            rvComics.findNavController().navigate(R.id.action_comicsFragment_to_comicDetail, bundle)
        }
    }

    private fun initRecyclerView(list: List<Comic>) {
        binding.rvComics.apply {
            layoutManager = LinearLayoutManager(this.context)

            adapter = ComicsAdapter(list) { comic ->
                viewModel.onItemSelected(comic.id)
            }
        }
    }
}