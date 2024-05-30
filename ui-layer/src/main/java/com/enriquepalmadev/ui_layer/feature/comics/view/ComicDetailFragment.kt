package com.enriquepalmadev.ui_layer.feature.comics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.databinding.FragmentComicDetailBinding
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.gone
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.loadImage
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.visible
import com.enriquepalmadev.ui_layer.feature.comics.viewmodel.ComicDetailViewModel
import com.enriquepalmadev.ui_layer.feature.comics.viewmodel.DetailState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ComicDetailFragment : Fragment() {
    companion object {
        const val KEY_ID = "id"
    }

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
                is DetailState.Error -> {
                    when (state.error) {
                        is FailureDomain.ApiError -> {
                            manageErrorApi(code = state.error.toString())
                        }

                        FailureDomain.UnknownHostError -> manageErrorApi(code = "400")
                        FailureDomain.Unauthorized -> manageErrorApi(code = "401")
                        FailureDomain.ApiError -> manageErrorApi(code = "")
                    }
                    manageLoader(false)
                }

                is DetailState.Exception -> {
                    manageErrorApi(state.message)
                    manageLoader(false)
                }

                is DetailState.ComicDetail -> {
                    manageLoader(false)
                    state.comicModel?.let { showComicDetail(it) }
                }

                is DetailState.FavoriteSuccess -> {

                }

                DetailState.Loading -> manageLoader(true)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    // Progress Bar
    private fun manageLoader(show: Boolean) {
        binding.detailProgressBar.apply {
            if (show) visible() else gone()
        }
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

                viewModel.addComicToFavorite(comicModel)
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

    private fun showErrorMessage(code: Int, message: String) {
        Snackbar.make(
            binding.comicDetail,
            "Código de error: $code \n$message",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun manageErrorApi(code: String) {
        binding.apply {
            ivError.visible()
            tvErrorCode.visible()
            tvErrorCode.text = getString(R.string.api_error)
        }
    }
}