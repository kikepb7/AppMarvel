package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.domain.model.ComicModel
import com.enriquepalmadev.appmarvel.data.repository.ComicProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {
    val state = MutableSharedFlow<State>()

    fun getComicsList() {
        viewModelScope.launch {
            val listComic = ComicProvider.comicsLists
            val favoriteComic = ComicProvider.comicsLists

            state.emit(State.ListReceived(listComic, favoriteComic))
        }
    }

    fun onItemSelected(id: Long) {
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
    data class NavigateToDetail(val comicId: Long): State()
}