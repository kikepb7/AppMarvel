package com.enriquepalmadev.ui_layer.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListFilterByNameUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfAllSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByAlphabetUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByStartYearUseCase
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.usecase.ClearAllLocalSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.GetLocalSeriesListUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.InsertAllSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.UpdateFavSerieUseCase
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private val getLocalSeriesListUseCase: GetLocalSeriesListUseCase,
    private val clearAllLocalSeriesUseCase: ClearAllLocalSeriesUseCase,
    private val updateFavSerieUseCase: UpdateFavSerieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilmSerieUIState())
    val uiState: StateFlow<FilmSerieUIState> = _uiState
    // private var localSeriesList: List<FilmSerieModel>? = listOf()

    private fun rescueFromDB(): List<FilmSerieModel> {
        var series : List<FilmSerieModel> = emptyList()
        viewModelScope.launch {
            getLocalSeriesListUseCase.getLocalSeriesList()
                .onStart { _uiState.update { updateLoading() } }
                .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                .collect {list ->
                    list?.let {
                        series = list
                        _uiState.update { updateSuccess(list) }
                    }
                }
        }
        return series
    }

    fun getAllSeriesListFromAPI() {
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
                                    responseEither.success?.let { list ->
                                        // Not cleaning cache before insert all series because we ignore equal items
                                        insertSeriesToDB(list)
                                        _uiState.update { updateSuccess(rescueFromDB()) }
                                    }
                                }
                            }
                        }
                    }
            }
    }

    fun orderListByStartYear() {
        viewModelScope.launch {
            if(rescueFromDB().isNotEmpty()){
                val series = rescueFromDB()
                fetchListOfSeriesOrderByStartYearUseCase
                    .getListOfSeriesOrderByStartYear(series)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect{ responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { updateFailure(responseEither.failure) }
                            }
                            is ResponseEither.Success -> {
                                _uiState.update { updateSuccess(responseEither.success) }
                            }
                        }
                    }
            } else {
                _uiState.update { updateFailure(FailureDomain.EmptyErrorDomain) }
            }
        }
    }

    fun orderListByAlphabet() {
        viewModelScope.launch {
            if(rescueFromDB().isNotEmpty()){
                val series = rescueFromDB()
                fetchListOfSeriesOrderByAlphabetUseCase
                    .getListOfSeriesOrderByAlphabet(series)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect{ responseEither ->
                        when (responseEither) {
                            is ResponseEither.Failure -> {
                                _uiState.update { updateFailure(responseEither.failure) }
                            }
                            is ResponseEither.Success -> {
                                _uiState.update { updateSuccess(responseEither.success) }
                            }
                        }
                    }
            } else {
                _uiState.update { updateFailure(FailureDomain.EmptyErrorDomain) }
            }
        }
    }

    fun filteringByName(newText: String) {
        viewModelScope.launch {
            if(rescueFromDB().isNotEmpty()){
                val series = rescueFromDB()
                fetchListFilterByNameUseCase
                    .getListFilterByName(newText, series)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect{ responseEither ->
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
            } else {
                _uiState.update { updateFailure(FailureDomain.EmptyErrorDomain) }
            }
        }
    }


    private fun clearAllSeriesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            clearAllLocalSeriesUseCase.clearAllLocalSeries()
        }
    }

    private fun insertSeriesToDB(seriesList: List<FilmSerieModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            insertAllSeriesUseCase.insertAllSeries(seriesList)
        }
    }

    fun updateFavSerie(id: Int, isFav: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            updateFavSerieUseCase.updateFavSerie(id, isFav)
        }
    }

    private fun updateLoading(): FilmSerieUIState {
        return FilmSerieUIState(isLoading = true)
    }

    private fun updateFailure(failure: FailureDomain): FilmSerieUIState {
        return FilmSerieUIState(isError = failure)
    }

    private fun updateSuccess(seriesList: List<FilmSerieModel>): FilmSerieUIState {
        return FilmSerieUIState(list = seriesList)
    }

    private fun updateNoItemsFound(): FilmSerieUIState {
        return FilmSerieUIState(itemsFound = false)
    }
}

data class FilmSerieUIState (
    val isError: FailureDomain? = null,
    val isLoading: Boolean = false,
    val list: List<FilmSerieModel> = emptyList(),
    val itemsFound: Boolean = true
)