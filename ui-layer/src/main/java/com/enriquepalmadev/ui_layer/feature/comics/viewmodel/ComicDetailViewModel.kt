package com.enriquepalmadev.ui_layer.feature.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import com.enriquepalmadev.domain_layer.feature.comics.usecase.FetchComicDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ComicDetailViewModel(
    private val fetchComicDetailUseCase: FetchComicDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state = _state.asStateFlow()

    fun getComicDetail(comicId: Int) {
        viewModelScope.launch {
            fetchComicDetailUseCase.fetchComicDetail(comicId)
                .onStart { _state.emit(DetailState.Loading) }
                .catch { _state.emit(DetailState.Exception(it.message.toString())) }
                .collect { result ->
                    when (result) {
                        is Either.Failure -> _state.emit(
                            DetailState.Error(error = result.error)
                        )

                        is Either.Success -> _state.emit(
                            DetailState.ComicDetail(result.data)
                        )
                    }
                }
        }
    }
}

// Different possible states
sealed class DetailState {
    data object Loading : DetailState()
    data class Error(val error: FailureDomain) : DetailState()
    data class Exception(val message: String) : DetailState()
    data class ComicDetail(val comicModel: ComicModel?) : DetailState()
}