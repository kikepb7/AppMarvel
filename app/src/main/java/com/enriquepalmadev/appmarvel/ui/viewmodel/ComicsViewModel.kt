package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.model.ComicModel
import com.enriquepalmadev.appmarvel.domain.usecase.FetchComicUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {
    private val fetchComicListUseCase = FetchComicUseCase()
    val state = MutableSharedFlow<State>()

    fun getComicsList() {
        viewModelScope.launch {
            val listComic = fetchComicListUseCase.fetchComicList()

            state.emit(State.ListReceived(listComic, listComic))
        }
    }

    fun onItemSelected(id: String) {
        viewModelScope.launch {
            state.emit(State.NavigateToDetail(id))
        }
    }
}

// Different possible states
sealed class State {
    data object Loading : State()
    data object Error : State()
    data class ListReceived(val listComicModels: List<ComicModel>, val listFavoriteComics: List<ComicModel>) : State()
    data class NavigateToDetail(val comicId: String): State()
}