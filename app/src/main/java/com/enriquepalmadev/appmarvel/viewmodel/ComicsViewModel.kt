package com.enriquepalmadev.appmarvel.viewmodel

import androidx.lifecycle.ViewModel
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.model.ComicProvider
import kotlinx.coroutines.flow.MutableSharedFlow

class ComicsViewModel : ViewModel() {
    val state = MutableSharedFlow<State>()
    // Diferencias MutableSharedFlow y Chanel

    /*
    by viewModels() -> Propietario Fragment

    by activityViewModels() ->
     */

    suspend fun getData() {
        val listComic = ComicProvider.comicsList

        state.emit(State.ListRecived(listComic))
    }
}

sealed class State {
    data object Loading : State()
    data object Error : State()
    data class ListRecived(val list: List<Comic>) : State()
}