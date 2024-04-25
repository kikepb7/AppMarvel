package com.enriquepalmadev.appmarvel.ui.feature.series.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.CustomError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.EmptyError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnauthorizedError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnknownHostError
import com.enriquepalmadev.appmarvel.databinding.ItemDetailsFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.feature.series.view.extensions.loadImage
import com.enriquepalmadev.appmarvel.ui.feature.series.viewmodel.ItemDetailFilmSerieViewModel
import com.enriquepalmadev.appmarvel.ui.feature.series.viewmodel.ItemDetailUIState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ItemDetailFilmSerieFragment : Fragment() {
    private lateinit var bindingItemDetailsFilmsSeries: ItemDetailsFilmsSeriesBinding
    private var serieModel: FilmSerieModel? = null
    private var idSerie : Int? = null
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

    private fun initObserver(){
        idViewModel.uiDetailState.onEach { uiState ->
            when(uiState){
                is ItemDetailUIState.Error -> {
                    when(uiState.error){
                        is CustomError -> {
                            setErrorView(uiState.error.code.toString(), uiState.error.msg)
                            showErrorView(true, R.drawable.groot_error)
                        }
                        UnauthorizedError -> {
                            setErrorView(getString(R.string.title_401), getString(R.string.msg_401))
                            showErrorView(true, R.drawable.thanos_unauthorized)
                        }

                        EmptyError -> {
                            setErrorView(getString(R.string.title_empty_error), getString(R.string.msg_empty_error))
                            showErrorView(true, R.drawable.deadpool_no_connection)
                        }

                        UnknownHostError -> {
                            setErrorView(getString(R.string.title_unknown_host_error), getString(R.string.msg_unknown_host_error))
                            showErrorView(true, R.drawable.captain_empty)
                        }
                    }
                    showLoading(false)
                }
                is ItemDetailUIState.Exception-> {
                    Toast.makeText(context, uiState.msgError, Toast.LENGTH_LONG).show()
                    showLoading(false)
                    showErrorView(false, null)
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

    private fun showLoading(visible: Boolean){
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

    private fun showErrorView(visible: Boolean, image: Int?){
        bindingItemDetailsFilmsSeries.apply {
            if (image != null) {
                imgError.setImageResource(image)
            }

            if(visible){
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

    private fun retrieveFilmOrSerie(){
        idSerie = args.idSerie
        idSerie?.let { idViewModel.getSerieById(it) }
    }

    private fun renderUi(){
        val completeImagePath = "${serieModel?.thumbnailPath}.${serieModel?.thumbnailExt}"

        bindingItemDetailsFilmsSeries.apply {
            detailFimsSeriesName.text = serieModel?.title
            detailImageFilmsSeries.loadImage(completeImagePath)
            detailFimsSeriesDescription.text = serieModel?.description
            detailFimsSeriesStarYear.text = serieModel?.startYear.toString()
        }
    }

}