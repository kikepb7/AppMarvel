package com.enriquepalmadev.appmarvel.ui.featureComics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import com.enriquepalmadev.appmarvel.domain.featureComics.usecase.FetchComicDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComicDetailViewModel : ViewModel() {

    private val fetchComicDetailUseCase = FetchComicDetailUseCase()
    val state = MutableStateFlow<DetailState>(DetailState.Loading)

    fun getComicDetail(comicId: Int) {
        viewModelScope.launch {
            val comic = fetchComicDetailUseCase.fetchComicDetail(comicId)

            comic?.let { state.emit(DetailState.ComicDetail(it)) }
        }
    }
}

// Different possible states
sealed class DetailState {
    data object Loading : DetailState()
    data object Error : DetailState()
    data class ComicDetail(val comicModel: ComicModel) : DetailState()
}