package com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import com.enriquepalmadev.appmarvel.domain.feature.comics.usecase.FetchComicDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ComicDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state = _state.asStateFlow()

    private val fetchComicDetailUseCase = FetchComicDetailUseCase()

    fun getComicDetail(comicId: Int) {
        viewModelScope.launch {
            fetchComicDetailUseCase.fetchComicDetail(comicId)
                .onStart { _state.emit(DetailState.Loading) }
                .catch { _state.emit(DetailState.Exception(it.message.toString())) }
                .collect { result ->
                    when (result) {
                        is Either.Failure -> _state.emit(DetailState.Error(error = result.error))
                        is Either.Success -> _state.emit(DetailState.ComicDetail(result.data))
                    }
                }
        }
    }
}

// Different possible states
sealed class DetailState {
    data object Loading : DetailState()
    data class Error(val error: Failure) : DetailState()
    data class Exception(val message: String): DetailState()
    data class ComicDetail(val comicModel: ComicModel?) : DetailState()
}