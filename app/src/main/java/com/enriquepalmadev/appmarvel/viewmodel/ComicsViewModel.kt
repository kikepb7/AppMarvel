package com.enriquepalmadev.appmarvel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.model.ComicProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {
    val state = MutableSharedFlow<State>()

    fun getComicsList() {
        viewModelScope.launch {
            val listComic = ComicProvider.comicsList

            state.emit(State.ListReceived(listComic))
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
    data class ListReceived(val listComics: List<Comic>) : State()
    data class NavigateToDetail(val comicId: Long): State()
}