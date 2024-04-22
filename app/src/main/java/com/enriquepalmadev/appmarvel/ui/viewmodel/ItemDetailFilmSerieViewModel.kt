package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.data.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.series.usecase.FetchSerieByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ItemDetailFilmSerieViewModel : ViewModel() {
    private val _uiDetailState = MutableStateFlow<ItemDetailUIState>(ItemDetailUIState.Loading)
    val uiDetailState: StateFlow<ItemDetailUIState> = _uiDetailState
    private val iGetSerieByIdUseCase = FetchSerieByIdUseCase()

    fun getSerieById(id: Int){
        viewModelScope.launch {
            iGetSerieByIdUseCase.getSerieById(id)
                .onStart { ItemDetailUIState.Loading }
                .catch { ItemDetailUIState.Exception(it.toString()) }
                .collect{responseEither ->
                    when(responseEither){
                        is ResponseEither.Failure -> {
                            _uiDetailState.emit(ItemDetailUIState.Error(responseEither.l))
                        }
                        is ResponseEither.Success -> {
                            _uiDetailState.emit(ItemDetailUIState.IdReceived(responseEither.r))
                        }
                    }
                }
        }
    }

}

sealed class ItemDetailUIState {
    data object Loading : ItemDetailUIState()
    data class IdReceived(val serie: FilmSerieModel) : ItemDetailUIState()
    data class Error (val error: Failure) : ItemDetailUIState()
    data class Exception(val msgError: String) : ItemDetailUIState()
}