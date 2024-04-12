package com.enriquepalmadev.appmarvel.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.series.usecase.FetchListFilterByNameUseCase
import com.enriquepalmadev.appmarvel.domain.series.usecase.FetchListOfAllSeriesUseCase
import com.enriquepalmadev.appmarvel.domain.series.usecase.FetchListOfSeriesOrderByAlphabetUseCase
import com.enriquepalmadev.appmarvel.domain.series.usecase.FetchListOfSeriesOrderByStartYearUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FilmSerieViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FilmSerieUIState>(FilmSerieUIState.Loading)
    val uiState : StateFlow<FilmSerieUIState> = _uiState

    private val iFetchListOfAllSeriesUseCase = FetchListOfAllSeriesUseCase()
    private val iFetchListOfSeriesOrderByStartYearUseCase = FetchListOfSeriesOrderByStartYearUseCase()
    private val iFetchListOfSeriesOrderByAlphabetUseCase = FetchListOfSeriesOrderByAlphabetUseCase()
    private val iFetchListFilterByNameUseCase = FetchListFilterByNameUseCase()

    private var allSeriesList: ArrayList<FilmSerieModel> = ArrayList()

    fun done(){
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ListReceived(allSeriesList))
        }
    }

    fun getAllSeriesListFromAPI(){
        viewModelScope.launch {
            iFetchListOfAllSeriesUseCase.getListOfAllSeries()
                .onStart { _uiState.emit(FilmSerieUIState.Loading)}
                .catch { _uiState.emit(FilmSerieUIState.Error(it.toString())) }
                .collect {
                    allSeriesList = it
                    _uiState.emit(FilmSerieUIState.ListReceived(it))
                }
        }
    }

    fun transferToDataDetail(filmOrSerie: FilmSerieModel){
        try {
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ItemClicked(filmOrSerie.id))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
    }

    // Function to order the items of the RecyclerView
    fun orderListBy(selectedItem: String, context: Context) {
        when(selectedItem) {
            context.getString(R.string.orderby_year) -> {
                viewModelScope.launch {
                    iFetchListOfSeriesOrderByStartYearUseCase
                        .getListOfSeriesOrderByStartYear(allSeriesList)
                        .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                        .catch { _uiState.emit(FilmSerieUIState.Error(it.toString())) }
                        .collect {
                            allSeriesList = it
                            _uiState.emit(FilmSerieUIState.ListReceived(it))
                        }
                }
            }

            context.getString(R.string.orderby_alphabet) -> {
                viewModelScope.launch {
                    iFetchListOfSeriesOrderByAlphabetUseCase
                        .getListOfSeriesOrderByAlphabet(allSeriesList)
                        .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                        .catch { _uiState.emit(FilmSerieUIState.Error(it.toString())) }
                        .collect {
                            allSeriesList = it
                            _uiState.emit(FilmSerieUIState.ListReceived(it))
                        }
                }
            }
            // getString(R.string.orderby_fav_first)-> arrayList.sortedBy { it.name }
            // getString(R.string.orderby_fav_only)-> arrayList.sortedBy { it.name }
        }
    }

    fun filteringByName(newText: String){
        viewModelScope.launch {
            iFetchListFilterByNameUseCase.getListFilterByName(newText, allSeriesList)
                .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                .catch { _uiState.emit(FilmSerieUIState.Error(it.toString())) }
                .collect{
                    _uiState.emit(FilmSerieUIState.ListReceived(it))
                }
        }
    }
}

sealed class FilmSerieUIState {
    data class Error(val msg: String) : FilmSerieUIState()
    data object Loading : FilmSerieUIState()
    data class ListReceived(val arrayList: ArrayList<FilmSerieModel>) : FilmSerieUIState()
    data class ItemClicked(val idSerie: Int) : FilmSerieUIState()
}
