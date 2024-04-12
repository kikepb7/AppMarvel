package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.enriquepalmadev.appmarvel.databinding.ItemDetailsFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.view.extensions.loadImage
import com.enriquepalmadev.appmarvel.ui.viewmodel.ItemDetailFilmSerieViewModel
import com.enriquepalmadev.appmarvel.ui.viewmodel.ItemDetailUIState
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
                    Toast.makeText(context, "Error: ${uiState.msg}", Toast.LENGTH_LONG).show()
                    // ProgressBar Gone
                }
                ItemDetailUIState.Loading -> {
                    retrieveFilmOrSerie()
                    // ProgressBar Visible
                }
                is ItemDetailUIState.IdReceived -> {
                    serieModel = uiState.serie
                    renderUi()
                    // ProgressBar Gone
                }
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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