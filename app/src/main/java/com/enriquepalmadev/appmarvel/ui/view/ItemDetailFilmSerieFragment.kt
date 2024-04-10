package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enriquepalmadev.appmarvel.databinding.ItemDetailsFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import com.enriquepalmadev.appmarvel.ui.view.extensions.loadImage

class ItemDetailFilmSerieFragment : Fragment() {
    private lateinit var bindingItemDetailsFilmsSeries: ItemDetailsFilmsSeriesBinding
    private var filmSerie: FilmSerie? = null
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
                is ItemDetailUIState.ListReceivedInLocal -> {
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
        filmSerie = filmsSeriesData?.getSerializable("objectFilmOrSerie") as FilmSerie?
        // This function is deprecated but the other function that is available can be used only from RetrofitBuilder level 33
    }

    private fun renderUi(){
        bindingItemDetailsFilmsSeries.detailFimsSeriesName.text = filmSerie?.title

        val completeImagePath = "${filmSerie?.thumbnailPath}.${filmSerie?.thumbnailExt}"
        filmSerie?.thumbnailPath?.let {
            bindingItemDetailsFilmsSeries.detailImageFilmsSeries.loadImage(completeImagePath)
        }
        bindingItemDetailsFilmsSeries.detailFimsSeriesDescription.text = filmSerie?.description
    }

    // CAMBIAR ESTADO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
}