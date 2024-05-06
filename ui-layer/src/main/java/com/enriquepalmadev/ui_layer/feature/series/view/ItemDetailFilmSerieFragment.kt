package com.enriquepalmadev.ui_layer.feature.series.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.databinding.ItemDetailsFilmsSeriesBinding
import com.enriquepalmadev.ui_layer.feature.series.view.utils.loadImage
import com.enriquepalmadev.ui_layer.feature.series.viewmodel.ItemDetailFilmSerieViewModel
import com.enriquepalmadev.ui_layer.feature.series.viewmodel.ItemDetailUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ItemDetailFilmSerieFragment : Fragment() {
    private lateinit var bindingItemDetailsFilmsSeries: ItemDetailsFilmsSeriesBinding
    private var serieModel: FilmSerieModel? = null
    private var idSerie: Int? = null
    private val idViewModel: ItemDetailFilmSerieViewModel by viewModels()

    private val args: ItemDetailFilmSerieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingItemDetailsFilmsSeries = ItemDetailsFilmsSeriesBinding.inflate(inflater)
        return bindingItemDetailsFilmsSeries.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserver()
    }

    private fun initObserver() {
        idViewModel.uiDetailState.onEach { uiState ->
            when (uiState) {
                is ItemDetailUIState.Error -> {
                    when (uiState.error) {
                        is FailureDomain.CustomErrorDomain -> {
                            setErrorView(uiState.error.code, uiState.error.msg)
                            showErrorView(true, R.drawable.groot_error)
                        }

                        FailureDomain.UnauthorizedErrorDomain -> {
                            setErrorView(getString(R.string.title_401), getString(R.string.msg_401))
                            showErrorView(true, R.drawable.thanos_unauthorized)
                        }

                        FailureDomain.EmptyErrorDomain -> {
                            setErrorView(
                                getString(R.string.title_empty_error),
                                getString(R.string.msg_empty_error)
                            )
                            showErrorView(true, R.drawable.deadpool_no_connection)
                        }

                        FailureDomain.UnknownHostErrorDomain -> {
                            setErrorView(
                                getString(R.string.title_unknown_host_error),
                                getString(R.string.msg_unknown_host_error)
                            )
                            showErrorView(true, R.drawable.captain_empty)
                        }

                        is FailureDomain.CoroutineErrorDomain -> {
                            setErrorView(
                                getString(R.string.title_coroutine_error),
                                getString(R.string.msg_coroutine_error)
                            )
                            showErrorView(true, R.drawable.groot_error)
                        }
                    }
                    showLoading(false)
                }

                ItemDetailUIState.Loading -> {
                    retrieveFilmOrSerie()
                    showErrorView(false, null)
                    showLoading(true)
                }

                is ItemDetailUIState.IdReceived -> {
                    serieModel = uiState.serie
                    renderUi()
                    showLoading(false)
                    showErrorView(false, null)
                }
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showLoading(visible: Boolean) {
        bindingItemDetailsFilmsSeries.apply {
            loading.isVisible = visible
            // detailFimsSeriesName.isVisible =!visible
        }
    }

    private fun setErrorView(code: String, msg: String) {
        bindingItemDetailsFilmsSeries.apply {
            txtErrorCode.text = code
            txtErrorMsg.text = msg
        }
    }

    private fun showErrorView(visible: Boolean, image: Int?) {
        bindingItemDetailsFilmsSeries.apply {
            if (image != null) {
                imgError.setImageResource(image)
            }

            if (visible) {
                imgError.isVisible = true
                txtErrorCode.isVisible = true
                txtErrorMsg.isVisible = true

                detailImageFilmsSeries.isVisible = false
                detailFimsSeriesName.isVisible = false
                detailFimsSeriesDescription.isVisible = false
                detailFimsSeriesStarYear.isVisible = false

            } else {
                imgError.isVisible = false
                txtErrorCode.isVisible = false
                txtErrorMsg.isVisible = false

                detailImageFilmsSeries.isVisible = true
                detailFimsSeriesName.isVisible = true
                detailFimsSeriesDescription.isVisible = true
                detailFimsSeriesStarYear.isVisible = true
            }

        }
    }

    private fun retrieveFilmOrSerie() {
        idSerie = args.idSerie
        idSerie?.let { idViewModel.getSerieById(it) }
    }

    private fun renderUi() {
        val completeImagePath = "${serieModel?.thumbnailPath}.${serieModel?.thumbnailExt}"

        bindingItemDetailsFilmsSeries.apply {
            detailFimsSeriesName.text = serieModel?.title
            detailImageFilmsSeries.loadImage(completeImagePath)
            detailFimsSeriesDescription.text = serieModel?.description
            detailFimsSeriesStarYear.text = serieModel?.startYear.toString()
        }
    }

}