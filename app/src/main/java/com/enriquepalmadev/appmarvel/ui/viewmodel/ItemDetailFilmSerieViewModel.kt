package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ItemDetailFilmSerieViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ItemDetailUIState>(ItemDetailUIState.Loading)
    val uiState: StateFlow<ItemDetailUIState> = _uiState

    /*
    private var filmSerieModel: FilmSerieModel? = null

    private fun retrieveFilmOrSerie(){
        val filmsSeriesData: Bundle? = arguments
        filmSerieModel = filmsSeriesData?.getSerializable("objectFilmOrSerie") as FilmSerieModel?
        // This function is deprecated but the other function that is available can be used only from API level 33
    }
    */
}

sealed class ItemDetailUIState {
    object Loading : ItemDetailUIState()
    data class ItemReceived(val arrayList: ArrayList<FilmSerieModel>) : ItemDetailUIState()
    data class Error (val msg: String) : ItemDetailUIState()
    //data class ShowDetail(): FilmSerieUIState()
}