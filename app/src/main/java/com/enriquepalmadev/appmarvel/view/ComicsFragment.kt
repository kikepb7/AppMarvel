package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicsBinding
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.model.ComicProvider
import com.enriquepalmadev.appmarvel.view.ComicDetail.Companion.KEY_ID
import com.enriquepalmadev.appmarvel.view.adapter.ComicsAdapter
import com.enriquepalmadev.appmarvel.viewmodel.ComicsViewModel
import com.enriquepalmadev.appmarvel.viewmodel.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ComicsFragment : Fragment() {
    private lateinit var binding: FragmentComicsBinding
    private val viewModel: ComicsViewModel  by viewModels()

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

        initObserver()

        initRecyclerView(binding.rvComics)
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when(state) {
                State.Error -> TODO()
                is State.ListRecived -> TODO()
                State.Loading -> TODO()
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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
            putString(KEY_ID, comic.id.toString())
//            putSerializable("comic", comic)
        }
        binding.apply {
            rvComics.findNavController().navigate(R.id.action_comicsFragment_to_comicDetail, bundle)
        }
    }
}