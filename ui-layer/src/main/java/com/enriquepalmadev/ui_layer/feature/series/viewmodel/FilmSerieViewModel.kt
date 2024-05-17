package com.enriquepalmadev.ui_layer.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListFilterByNameUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfAllSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByAlphabetUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByStartYearUseCase
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmSerieViewModel @Inject constructor(
    private val iFetchListOfAllSeriesUseCase: FetchListOfAllSeriesUseCase,
    private val iFetchListOfSeriesOrderByStartYearUseCase: FetchListOfSeriesOrderByStartYearUseCase,
    private val iFetchListOfSeriesOrderByAlphabetUseCase: FetchListOfSeriesOrderByAlphabetUseCase,
    private val iFetchListFilterByNameUseCase: FetchListFilterByNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilmSerieUIState())
    val uiState: StateFlow<FilmSerieUIState> = _uiState

    private var allSeriesList: List<FilmSerieModel>? = listOf()

    private fun customFilterList(series: List<FilmSerieModel>): List<FilmSerieModel> {
        return series.filter {
            it.description.isNullOrEmpty().not()
        }.sortedByDescending { it.startYear }
    }

    private fun done() {
        viewModelScope.launch {
            allSeriesList?.let { list ->
                _uiState.update {
                    it.copy(list = list, isLoading = false, isError = null)
                }
            }
        }
    }

    fun getAllSeriesListFromAPI() {
        if (allSeriesList?.isEmpty() == true) {
            viewModelScope.launch {
                iFetchListOfAllSeriesUseCase.getListOfAllSeries()
                    .onStart { _uiState.update { it.copy(list = emptyList(), isLoading = true, isError = null) } }
                    .catch { _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = FailureDomain.CoroutineErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = responseEither.l) }
                            }

                            is ResponseEither.Success -> {
                                if (responseEither.r?.isEmpty() == true) {
                                    _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = FailureDomain.EmptyErrorDomain) }
                                } else {
                                    allSeriesList = responseEither.r?.let { customFilterList(it) }
                                    allSeriesList?.let { list ->
                                        _uiState.update { it.copy(list = list, isLoading = false, isError = null) }
                                    }
                                }
                            }
                        }
                    }
            }
        } else {
            done()
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
            allSeriesList?.let { seriesList ->
                iFetchListOfSeriesOrderByStartYearUseCase
                    .getListOfSeriesOrderByStartYear(seriesList)
                    .onStart { _uiState.update { it.copy(list = emptyList(), isLoading = true, isError = null) } }
                    .catch { _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = FailureDomain.CoroutineErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = responseEither.l) }
                            }

                            is ResponseEither.Success -> {
                                allSeriesList = responseEither.r
                                _uiState.update { it.copy(list = responseEither.r, isLoading = false, isError = null) }
                            }
                        }
                    }
            }
        }
    }

    fun orderListByAlphabet() {
        viewModelScope.launch {
            allSeriesList?.let { seriesList ->
                iFetchListOfSeriesOrderByAlphabetUseCase
                    .getListOfSeriesOrderByAlphabet(seriesList)
                    .onStart { _uiState.update { it.copy(list = emptyList(), isLoading = true, isError = null) } }
                    .catch { _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = FailureDomain.CoroutineErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = responseEither.l) }
                            }

                            is ResponseEither.Success -> {
                                allSeriesList = responseEither.r
                                _uiState.update { it.copy(list = responseEither.r, isLoading = false, isError = null) }
                            }
                        }
                    }
            }
        }
    }

    fun filteringByName(newText: String) {
        viewModelScope.launch {
            allSeriesList?.let { seriesList ->
                iFetchListFilterByNameUseCase.getListFilterByName(newText, seriesList)
                    .onStart { _uiState.update { it.copy(list = emptyList(), isLoading = true, isError = null) } }
                    .catch { _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = FailureDomain.CoroutineErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { it.copy(list = emptyList(), isLoading = false, isError = responseEither.l) }
                            }

                            is ResponseEither.Success -> {
                                _uiState.update { FilmSerieUIState(list = responseEither.r) }
                            }
                        }
                    }
            }
        }
    }
}

data class FilmSerieUIState (
    val isError: FailureDomain? = null,
    val isLoading: Boolean = false,
    val list: List<FilmSerieModel> = emptyList()
)