package com.enriquepalmadev.appmarvel.ui.featureComics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicDetailBinding
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.featureComics.view.utils.loadImage
import com.enriquepalmadev.appmarvel.ui.featureComics.viewmodel.ComicDetailViewModel
import com.enriquepalmadev.appmarvel.ui.featureComics.viewmodel.DetailState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ComicDetailFragment : Fragment() {

    companion object { const val KEY_ID = "id" }
    private var isFavorite = false
    private val comicId by lazy { arguments?.getInt(KEY_ID) }
    private lateinit var binding: FragmentComicDetailBinding
    private val viewModel: ComicDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        comicId?.let { id ->
            viewModel.getComicDetail(id)
        }
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when (state) {
                is DetailState.ComicDetail -> {
                    manageLoader(false)
                    state.comicModel?.let { showComicDetail(it) }
                }
                DetailState.Error -> { manageLoader(false) }
                DetailState.Loading -> manageLoader(true)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    // Progress Bar
    private fun manageLoader(show: Boolean) {
        if (show) binding.detailProgressBar.visibility = View.VISIBLE
        else binding.detailProgressBar.visibility = View.GONE
    }

    // Comic Detail
    private fun showComicDetail(comicModel: ComicModel) {
        binding.apply {
            ivComic.loadImage(comicModel.thumbnail)
            comicPages.text = comicModel.pageCount.toString()
            tvTitle.text = comicModel.title
            tvDescription.text = comicModel.description

            btnFavorite.setOnClickListener {
                isFavorite = !isFavorite
                updateFavoriteIcon()
            }

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.comicsFragment)
            }
        }
    }

    // Favorite button
    private fun updateFavoriteIcon() {
        binding.btnFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_solid_heart else R.drawable.ic_line_heart
        )
    }
}