package com.enriquepalmadev.ui_layer.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListFilterByNameUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfAllSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByAlphabetUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchListOfSeriesOrderByStartYearUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.GetLocalSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.InsertAllSeriesUseCase
import com.enriquepalmadev.domain_layer.feature.series.usecase.UpdateFavSerieUseCase
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
    private val updateFavSeriesUseCase: UpdateFavSerieUseCase,
    private val getAllSeriesUseCase: GetLocalSeriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilmSerieUIState())
    val uiState: StateFlow<FilmSerieUIState> = _uiState

    private var apiSeries : List<FilmSerieModel> = emptyList()
    private var dbSeries : List<FilmSerieModel> = emptyList()

    fun getAllSeriesListFromAPI() {
            viewModelScope.launch {
                if (apiSeries.isEmpty()) {
                fetchListOfAllSeriesUseCase.getListOfAllSeries()
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect { responseEither ->
                        when (responseEither) {
                            is Either.Error -> {
                                _uiState.update { updateFailure(responseEither.error) }
                            }
                            is Either.Success -> { // If it is success but the list comes empty...
                                if (responseEither.data?.isEmpty() == true) {
                                    _uiState.update { updateFailure(FailureDomain.EmptyErrorDomain) }
                                } else {
                                    responseEither.data?.let { list ->
                                        // Not cleaning cache before insert all series because we ignore equal items
                                        apiSeries = list
                                        insertSeriesToDB(list)
                                        // TODO -> comparo listas y seteo isFav de db a apiList
                                        getSeriesFromDB()
                                        apiSeries = compareFavAttr(apiSeries, dbSeries)
                                        _uiState.update { updateSuccess(apiSeries) }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    done()
                }
            }
    }

    private fun done() {
        _uiState.update { updateSuccess(apiSeries) }
    }

    fun orderListByStartYear() {
        viewModelScope.launch {
            if(apiSeries.isNotEmpty()){
                fetchListOfSeriesOrderByStartYearUseCase
                    .getListOfSeriesOrderByStartYear(apiSeries)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect{ responseEither ->
                        when (responseEither) {
                            is Either.Error -> {
                                _uiState.update { updateFailure(responseEither.error) }
                            }
                            is Either.Success -> {
                                _uiState.update { updateSuccess(responseEither.data) }
                                apiSeries = responseEither.data
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
            if(apiSeries.isNotEmpty()){
                fetchListOfSeriesOrderByAlphabetUseCase
                    .getListOfSeriesOrderByAlphabet(apiSeries)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect{ responseEither ->
                        when (responseEither) {
                            is Either.Error -> {
                                _uiState.update { updateFailure(responseEither.error) }
                            }
                            is Either.Success -> {
                                _uiState.update { updateSuccess(responseEither.data) }
                                apiSeries = responseEither.data
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
            if(apiSeries.isNotEmpty()){
                fetchListFilterByNameUseCase
                    .getListFilterByName(newText, apiSeries)
                    .onStart { _uiState.update { updateLoading() } }
                    .catch { _uiState.update { updateFailure(FailureDomain.AnotherErrorDomain) } }
                    .collect{ responseEither ->
                        when (responseEither) {
                            is Either.Error -> {
                                _uiState.update { updateFailure(responseEither.error) }
                            }
                            is Either.Success -> {
                                if(responseEither.data.isEmpty()){
                                    _uiState.update { updateNoItemsFound() }
                                } else {
                                    _uiState.update { updateSuccess(responseEither.data) }
                                }
                            }
                        }
                    }
            } else {
                _uiState.update { updateFailure(FailureDomain.EmptyErrorDomain) }
            }
        }
    }

    private suspend fun insertSeriesToDB(seriesList: List<FilmSerieModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            insertAllSeriesUseCase.insertAllSeries(seriesList)
        }.join()
    }

    fun updateFavSerie(id: Int, isFav: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            updateFavSeriesUseCase.updateFavSerie(id, isFav)
            apiSeries.forEach { if(it.id == id) it.isFav = isFav }
        }
    }

    private suspend fun getSeriesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            dbSeries = getAllSeriesUseCase.getAllSeries()
        }.join()
    }

    private fun compareFavAttr(apiList : List<FilmSerieModel>, dbList : List<FilmSerieModel>) : List<FilmSerieModel> {
        val list: List<FilmSerieModel> = apiList
        dbList.forEach { dbItem ->
            apiList.forEach { apiItem ->
                if(dbItem.id==apiItem.id) apiItem.isFav = dbItem.isFav
            }
        }
        return list
    }

    // Saco una lista con items con isFav = true
    // Paso esa lista de ids por parametro a compose
    private fun favIdList (apiList : List<FilmSerieModel>, dbList : List<FilmSerieModel>) : List<Int> {
        val idList = mutableListOf<Int>()
        for (item in apiList) {
            if (!dbList.contains(item)) {
                idList.add(item.id)
            }
        }
        return idList
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