package com.enriquepalmadev.appmarvel.ui.feature.comics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Unauthorized
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.UnknownHostError
import com.enriquepalmadev.appmarvel.databinding.FragmentComicDetailBinding
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.view.utils.loadImage
import com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel.ComicDetailViewModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel.DetailState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
                        is ApiError -> {
                            showErrorMessage(code = state.error.code, message = state.error.message)
                            manageErrorApi(code = state.error.code.toString())
                        }

                        UnknownHostError -> manageErrorApi(code = "400")
                        Unauthorized -> manageErrorApi(code = "401")
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

    private fun showErrorMessage(code: Int, message: String) {
        Snackbar.make(
            binding.comicDetail,
            "Código de error: $code \n$message",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun manageErrorApi(code: String) {
        binding.apply {
            ivError.visibility = View.VISIBLE
            tvErrorCode.visibility = View.VISIBLE
            tvErrorCode.text = "Error ${code}"
        }
    }
 }