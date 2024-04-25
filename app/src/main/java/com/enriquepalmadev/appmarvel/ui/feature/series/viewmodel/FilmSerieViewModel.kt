package com.enriquepalmadev.appmarvel.ui.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.feature.series.usecase.FetchListFilterByNameUseCase
import com.enriquepalmadev.appmarvel.domain.feature.series.usecase.FetchListOfAllSeriesUseCase
import com.enriquepalmadev.appmarvel.domain.feature.series.usecase.FetchListOfSeriesOrderByAlphabetUseCase
import com.enriquepalmadev.appmarvel.domain.feature.series.usecase.FetchListOfSeriesOrderByStartYearUseCase
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

    private var allSeriesList: List<FilmSerieModel>? = listOf()

    private fun customFilterList(series: List<FilmSerieModel>) : List<FilmSerieModel>{
        return series.filter {
            !it.description.isNullOrEmpty()
        } .sortedByDescending {  it.startYear }
    }

    fun done(){
        viewModelScope.launch {
            allSeriesList?.let { FilmSerieUIState.ListReceived(it) }?.let { _uiState.emit(it) }
        }
    }

    fun getAllSeriesListFromAPI(){
        if (allSeriesList?.isEmpty() == true){
            viewModelScope.launch {
                iFetchListOfAllSeriesUseCase.getListOfAllSeries()
                    .onStart { _uiState.emit(FilmSerieUIState.Loading)}
                    .catch { _uiState.emit(FilmSerieUIState.Exception(it.message.toString())) }
                    .collect { responseEither ->
                        when(responseEither){
                            is ResponseEither.Failure -> {
                                _uiState.emit(FilmSerieUIState.Error(responseEither.l))
                            }
                            is ResponseEither.Success -> {
                                if(responseEither.r?.isEmpty() == true){
                                    _uiState.emit(FilmSerieUIState.Exception("Error here!"))
                                } else {
                                    allSeriesList = responseEither.r?.let { customFilterList(it) }
                                    allSeriesList?.let {
                                        FilmSerieUIState.ListReceived(it)
                                    }?.let { _uiState.emit(it) }
                                }
                            }
                        }
                    }
            }
        } else {
            done()
        }
    }

    fun transferToDataDetail(filmOrSerie: FilmSerieModel){
        try {
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ItemClicked(filmOrSerie.id))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Exception("Error here!"))
            }
        }
    }

    fun orderListByStartYear(){
        viewModelScope.launch {
            allSeriesList?.let {
                iFetchListOfSeriesOrderByStartYearUseCase
                    .getListOfSeriesOrderByStartYear(it)
                    .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                    .catch { _uiState.emit(FilmSerieUIState.Exception("Error here!")) }
                    .collect {responseEither ->
                        when(responseEither){
                            is ResponseEither.Failure -> {
                                _uiState.emit(FilmSerieUIState.Error(responseEither.l))
                            }
                            is ResponseEither.Success -> {
                                allSeriesList = responseEither.r
                                _uiState.emit(FilmSerieUIState.ListReceived(responseEither.r))
                            }
                        }
                    }
            }
        }
    }

    fun orderListByAlphabet(){
        viewModelScope.launch {
            allSeriesList?.let {
                iFetchListOfSeriesOrderByAlphabetUseCase
                    .getListOfSeriesOrderByAlphabet(it)
                    .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                    .catch { _uiState.emit(FilmSerieUIState.Exception("Error here!")) }
                    .collect {responseEither ->
                        when(responseEither){
                            is ResponseEither.Failure -> {
                                _uiState.emit(FilmSerieUIState.Error(responseEither.l))
                            }
                            is ResponseEither.Success -> {
                                allSeriesList = responseEither.r
                                _uiState.emit(FilmSerieUIState.ListReceived(responseEither.r))
                            }
                        }
                    }
            }
        }
    }

    fun filteringByName(newText: String){
        viewModelScope.launch {
            allSeriesList?.let {
                iFetchListFilterByNameUseCase.getListFilterByName(newText, it)
                    .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                    .catch { _uiState.emit(FilmSerieUIState.Exception("Error here!")) }
                    .collect{responseEither ->
                        when(responseEither){
                            is ResponseEither.Failure -> {
                                _uiState.emit(FilmSerieUIState.Error(responseEither.l))
                            }
                            is ResponseEither.Success -> {
                                _uiState.emit(FilmSerieUIState.ListReceived(responseEither.r))
                            }
                        }
                    }
            }
        }
    }
}

sealed class FilmSerieUIState {
    data class Error(val error: Failure) : FilmSerieUIState()
    data class Exception(val msgError: String): FilmSerieUIState()
    data object Loading : FilmSerieUIState()
    data class ListReceived(val list: List<FilmSerieModel>) : FilmSerieUIState()
    data class ItemClicked(val idSerie: Int) : FilmSerieUIState()
}
