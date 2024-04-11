package com.enriquepalmadev.appmarvel.ui.featureComics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import com.enriquepalmadev.appmarvel.domain.featureComics.usecase.FetchComicUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {
    private val fetchComicListUseCase = FetchComicUseCase()
    val state = MutableSharedFlow<State>()

    fun getComicsList() {
        viewModelScope.launch {
            fetchComicListUseCase.fetchComicList()
                .onStart { state.emit(State.Loading) }
                .catch { state.emit(State.Error) }
                .collect { state.emit(State.ListReceived(it)) }
        }
    }

    fun onItemSelected(id: Int) {
        viewModelScope.launch {
            state.emit(State.NavigateToDetail(id))
        }
    }
}

// Different possible states
sealed class State {
    data object Loading : State()
    data object Error : State()
    data class ListReceived(val listComicModels: List<ComicModel>?) : State()
    data class NavigateToDetail(val comicId: Int): State()
}