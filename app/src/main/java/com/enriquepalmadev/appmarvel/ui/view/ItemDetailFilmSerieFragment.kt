package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enriquepalmadev.appmarvel.databinding.ItemDetailsFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.view.extensions.loadImage

class ItemDetailFilmSerieFragment : Fragment() {
    private lateinit var bindingItemDetailsFilmsSeries: ItemDetailsFilmsSeriesBinding
    private var filmSerieModel: FilmSerieModel? = null
    // private val itemdetailViewModel: ItemDetailFilmSerieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingItemDetailsFilmsSeries = ItemDetailsFilmsSeriesBinding.inflate(inflater)
        return bindingItemDetailsFilmsSeries.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //initObserver()
        retrieveFilmOrSerie()
        renderUi()
    }

    /*
    private fun initObserver(){
        itemdetailViewModel.uiState.onEach { uiState ->
            when(uiState){
                is ItemDetailUIState.Error -> {
                    Toast.makeText(context, "Error...", Toast.LENGTH_LONG).show()
                    // ProgressBar Gone
                }
                ItemDetailUIState.Loading -> {
                    // ProgressBar Visible

                }
                is ItemDetailUIState.ListReceived -> {
                    // initRecyclerView(fsAdapter, fsBinding.rvFilmsSeries)
                    // initRecyclerView(State, List)
                    // ProgressBar Gone
                }
            }
        }
    }

     */

    private fun retrieveFilmOrSerie(){
        val filmsSeriesData: Bundle? = arguments
        filmSerieModel = filmsSeriesData?.getSerializable("objectFilmOrSerie") as FilmSerieModel?
        // This function is deprecated but the other function that is available can be used only from API level 33
    }

    private fun renderUi(){
        bindingItemDetailsFilmsSeries.detailFimsSeriesName.text = filmSerieModel?.name
        filmSerieModel?.cover?.let { myCover ->
            bindingItemDetailsFilmsSeries.detailImageFilmsSeries.loadImage(myCover)
        }
        bindingItemDetailsFilmsSeries.detailFimsSeriesDescription.text = filmSerieModel?.description
    }

    // CAMBIAR ESTADO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
}