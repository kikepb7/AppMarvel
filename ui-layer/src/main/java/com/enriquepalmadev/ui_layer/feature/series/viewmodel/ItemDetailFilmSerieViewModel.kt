package com.enriquepalmadev.ui_layer.feature.series.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.usecase.FetchSerieByIdUseCase
import com.enriquepalmadev.domain_layer.feature.series.utils.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEitherDomain
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
    // private val iGetSerieByIdUseCase = FetchSerieByIdUseCase()

    fun getSerieById(id: Int) {
        viewModelScope.launch {
            iGetSerieByIdUseCase.getSerieById(id)
                .onStart { ItemDetailUIState.Loading }
                .catch { ItemDetailUIState.Error(FailureDomain.CoroutineError) }
                .collect { responseEither ->
                    when (responseEither) {
                        is ResponseEitherDomain.Failure -> {
                            _uiDetailState.emit(ItemDetailUIState.Error(responseEither.error))
                        }

                        is ResponseEitherDomain.Success -> {
                            _uiDetailState.emit(ItemDetailUIState.IdReceived(responseEither.data))
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