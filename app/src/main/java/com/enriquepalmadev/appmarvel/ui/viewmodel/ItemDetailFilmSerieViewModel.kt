package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                .catch { ItemDetailUIState.Error(it.toString()) }
                .collect{
                    _uiDetailState.emit(ItemDetailUIState.IdReceived(it))
                }
        }
    }

}

sealed class ItemDetailUIState {
    data object Loading : ItemDetailUIState()
    data class IdReceived(val serie: FilmSerieModel) : ItemDetailUIState()
    data class Error (val msg: String) : ItemDetailUIState()
}