package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.enriquepalmadev.appmarvel.databinding.FragmentComicDetailBinding
import com.enriquepalmadev.appmarvel.domain.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.view.utils.loadImage
import com.enriquepalmadev.appmarvel.ui.viewmodel.ComicDetailViewModel
import com.enriquepalmadev.appmarvel.ui.viewmodel.DetailState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ComicDetailFragment : Fragment() {

    companion object {
        const val KEY_ID = "id"
    }

    private val comicId by lazy { arguments?.getLong(KEY_ID) }
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
                    // delay(500)  TODO --> eliminar cuando llamemos al servicio
                    hideLoader()
                    showComicDetail(state.comicModel)
                }

                DetailState.Error -> {
                    hideLoader()
                }

                DetailState.Loading -> {
                    showLoader()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    // Progress Bar
    private fun hideLoader() {
        binding.detailProgressBar.visibility = View.GONE
    }

    private fun showLoader() {
        binding.detailProgressBar.visibility = View.VISIBLE
    }


    // Comic
    private fun showComicDetail(comicModel: ComicModel) {
        binding.apply {
            ivComic.loadImage(comicModel.image)
            comicId.text = comicModel.id.toString()
            tvTitle.text = comicModel.title
            tvDescription.text = comicModel.description
            tvPrice.text = comicModel.price.toString()
        }
    }

    // Favorite button
    fun clickFavorite() {

    }
}