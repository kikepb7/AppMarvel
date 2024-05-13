package com.enriquepalmadev.ui_layer.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListFilterByNameUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfAllSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByAlphabetUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByStartYearUseCase
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmSerieViewModel @Inject constructor(
    private val iFetchListOfAllSeriesUseCase: FetchListOfAllSeriesUseCase,
    private val iFetchListOfSeriesOrderByStartYearUseCase: FetchListOfSeriesOrderByStartYearUseCase,
    private val iFetchListOfSeriesOrderByAlphabetUseCase: FetchListOfSeriesOrderByAlphabetUseCase,
    private val iFetchListFilterByNameUseCase: FetchListFilterByNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FilmSerieUIState>(FilmSerieUIState.Loading)
    val uiState: StateFlow<FilmSerieUIState> = _uiState

    // private val iFetchListOfAllSeriesUseCase = FetchListOfAllSeriesUseCase()
    // private val iFetchListOfSeriesOrderByStartYearUseCase = FetchListOfSeriesOrderByStartYearUseCase()
    // private val iFetchListOfSeriesOrderByAlphabetUseCase = FetchListOfSeriesOrderByAlphabetUseCase()
    // private val iFetchListFilterByNameUseCase = FetchListFilterByNameUseCase()

    private var allSeriesList: List<FilmSerieModel>? = listOf()

    private fun customFilterList(series: List<FilmSerieModel>): List<FilmSerieModel> {
        return series.filter {
            !it.description.isNullOrEmpty()
        }.sortedByDescending { it.startYear }
    }

    fun done() {
        viewModelScope.launch {
            allSeriesList?.let { FilmSerieUIState.ListReceived(it) }?.let { _uiState.emit(it) }
        }
    }

    fun getAllSeriesListFromAPI() {
        if (allSeriesList?.isEmpty() == true) {
            viewModelScope.launch {
                iFetchListOfAllSeriesUseCase.getListOfAllSeries()
                    .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                    .catch { _uiState.emit(FilmSerieUIState.Error(FailureDomain.CoroutineErrorDomain)) }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.emit(FilmSerieUIState.Error(responseEither.l))
                            }

                            is ResponseEither.Success -> {
                                if (responseEither.r?.isEmpty() == true) {
                                    _uiState.emit(FilmSerieUIState.Error(FailureDomain.CoroutineErrorDomain))
                                } else {
                                    allSeriesList =
                                        responseEither.r?.let { customFilterList(it) }
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

    fun transferToDataDetail(id: Int) {
        try {
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ItemClicked(id))
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(FailureDomain.CoroutineErrorDomain))
            }
        }
    }

    /* TODO() This function is thought to the database persist
    fun favSerie(id: Int, favState: String) {
        when (favState){
            "on" -> {
                TODO() // Add film to fav list
            }
            "off" -> {
                TODO() // Remove film to fav list
            }
        }
    }
     */

    fun orderListByStartYear() {
        viewModelScope.launch {
            allSeriesList?.let {
                iFetchListOfSeriesOrderByStartYearUseCase
                    .getListOfSeriesOrderByStartYear(it)
                    .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                    .catch { _uiState.emit(FilmSerieUIState.Error(FailureDomain.CoroutineErrorDomain)) }
                    .collect { responseEither ->
                        when (responseEither) {
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

    fun orderListByAlphabet() {
        viewModelScope.launch {
            allSeriesList?.let {
                iFetchListOfSeriesOrderByAlphabetUseCase
                    .getListOfSeriesOrderByAlphabet(it)
                    .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                    .catch { _uiState.emit(FilmSerieUIState.Error(FailureDomain.CoroutineErrorDomain)) }
                    .collect { responseEither ->
                        when (responseEither) {
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

    fun filteringByName(newText: String) {
        viewModelScope.launch {
            allSeriesList?.let {
                iFetchListFilterByNameUseCase.getListFilterByName(newText, it)
                    .onStart { _uiState.emit(FilmSerieUIState.Loading) }
                    .catch { _uiState.emit(FilmSerieUIState.Error(FailureDomain.CoroutineErrorDomain)) }
                    .collect { responseEither ->
                        when (responseEither) {
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
    data class Error(val error: FailureDomain) : FilmSerieUIState()
    data object Loading : FilmSerieUIState()
    data class ListReceived(val list: List<FilmSerieModel>) : FilmSerieUIState()
    data class ItemClicked(val idSerie: Int) : FilmSerieUIState()
}