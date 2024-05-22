package com.enriquepalmadev.ui_layer.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListFilterByNameUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfAllSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByAlphabetUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByStartYearUseCase
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.usecase.GetLocalSeriesListUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.InsertAllSeriesUseCase
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
    private val fetchListOfAllSeriesUseCase: FetchListOfAllSeriesUseCase,
    private val fetchListOfSeriesOrderByStartYearUseCase: FetchListOfSeriesOrderByStartYearUseCase,
    private val fetchListOfSeriesOrderByAlphabetUseCase: FetchListOfSeriesOrderByAlphabetUseCase,
    private val fetchListFilterByNameUseCase: FetchListFilterByNameUseCase,
    private val insertAllSeriesUseCase: InsertAllSeriesUseCase,
    private val getLocalSeriesListUseCase: GetLocalSeriesListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilmSerieUIState())
    val uiState: StateFlow<FilmSerieUIState> = _uiState
    private var localSeriesList: List<FilmSerieModel>? = listOf()

    private fun done() {
        viewModelScope.launch {
            localSeriesList?.let { list ->
                _uiState.update { updateSuccess(list) }
            }
        }
    }

    fun getAllSeriesListFromAPI() {
        if (localSeriesList?.isEmpty() == true) {
            viewModelScope.launch {
                fetchListOfAllSeriesUseCase.getListOfAllSeries()
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { updateFailure(responseEither.failure) }
                            }
                            is ResponseEither.Success -> { // If it is success but the list comes empty...
                                if (responseEither.success?.isEmpty() == true) {
                                    _uiState.update { updateFailure(FailureDomain.EmptyErrorDomain) }
                                } else {
                                    responseEither.success?.let { insertAllSeriesUseCase.insertAllSeries(it) }
                                    // TODO() Guardar la lista en la BBDD (Limpiandola antes)
                                    localSeriesList = responseEither.success
                                    localSeriesList?.let { list ->
                                        _uiState.update { updateSuccess(list) }
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

    fun orderListByStartYear() {
        viewModelScope.launch {
            localSeriesList?.let { seriesList ->
                fetchListOfSeriesOrderByStartYearUseCase
                    .getListOfSeriesOrderByStartYear(seriesList)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { updateFailure(responseEither.failure) }
                            }
                            is ResponseEither.Success -> {
                                localSeriesList = responseEither.success
                                _uiState.update { updateSuccess(responseEither.success) }
                            }
                        }
                    }
            }
        }
    }

    fun orderListByAlphabet() {
        viewModelScope.launch {
            localSeriesList?.let { seriesList ->
                fetchListOfSeriesOrderByAlphabetUseCase
                    .getListOfSeriesOrderByAlphabet(seriesList)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { updateFailure(responseEither.failure) }
                            }
                            is ResponseEither.Success -> {
                                localSeriesList = responseEither.success
                                _uiState.update { updateSuccess(responseEither.success) }
                            }
                        }
                    }
            }
        }
    }

    fun filteringByName(newText: String) {
        viewModelScope.launch {
            localSeriesList?.let { seriesList ->
                fetchListFilterByNameUseCase.getListFilterByName(newText, seriesList)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { updateFailure(responseEither.failure) }
                            }
                            is ResponseEither.Success -> {
                                if(responseEither.success.isEmpty()){
                                    _uiState.update { updateNoItemsFound() }
                                } else {
                                    _uiState.update { updateSuccess(responseEither.success) }
                                }
                            }
                        }
                    }
            }
        }
    }
}

private fun updateLoading(): FilmSerieUIState {
    return FilmSerieUIState(isLoading = true)
}

private fun updateFailure(failure : FailureDomain): FilmSerieUIState {
    return FilmSerieUIState(isError = failure)
}

private fun updateSuccess(seriesList : List<FilmSerieModel>): FilmSerieUIState {
    return FilmSerieUIState(list = seriesList)
}

private fun updateNoItemsFound(): FilmSerieUIState {
    return FilmSerieUIState(itemsFound = false)
}

data class FilmSerieUIState (
    val isError: FailureDomain? = null,
    val isLoading: Boolean = false,
    val list: List<FilmSerieModel> = emptyList(),
    val itemsFound: Boolean = true
)