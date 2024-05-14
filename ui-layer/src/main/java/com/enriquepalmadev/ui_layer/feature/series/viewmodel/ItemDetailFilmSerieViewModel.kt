package com.enriquepalmadev.ui_layer.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchSerieByIdUseCase
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailFilmSerieViewModel @Inject constructor(
    private val iGetSerieByIdUseCase: FetchSerieByIdUseCase
) : ViewModel() {
    private val _uiDetailState = MutableStateFlow<ItemDetailUIState>(ItemDetailUIState.Loading)
    val uiDetailState: StateFlow<ItemDetailUIState> = _uiDetailState

    fun getSerieById(id: Int) {
        viewModelScope.launch {
            iGetSerieByIdUseCase.getSerieById(id)
                .onStart { ItemDetailUIState.Loading }
                .catch { ItemDetailUIState.Error(FailureDomain.CoroutineErrorDomain) }
                .collect { responseEither ->
                    when (responseEither) {
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
    data class Error(val error: FailureDomain) : ItemDetailUIState()
}