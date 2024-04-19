package com.enriquepalmadev.appmarvel.ui.feature.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import com.enriquepalmadev.appmarvel.domain.feature.comics.usecase.FetchComicUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {
    private val fetchComicListUseCase = FetchComicUseCase()
    val state = MutableSharedFlow<State>() // TODO --> Cambiar a State
    private var comicList : List<ComicModel> = emptyList()

    fun getComicsList() {
        viewModelScope.launch {
            fetchComicListUseCase.fetchComicList()
                .onStart { state.emit(State.Loading) }
                .catch { e ->
                    state.emit(State.Error(ApiError(code = 0, message = e.message ?: "Unknown error")))
                }
                .collect { result ->
                    when (result) {
                        is Either.Failure ->
                            state.emit(State.Error(error = result.error))

                        is Either.Success -> {
                            if (!result.data.isNullOrEmpty()) {
                                val filteredList = result.data.filter { comic ->
                                    (!comic.description.isNullOrEmpty() && comic.description != "#N/A") &&
                                            (comic.thumbnail != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
                                }
                                comicList = filteredList // Guardamos la lista filtrada en una variable
                                state.emit(State.ListReceived(comicList))
                                //} else {
                                //    state.emit(State.EmptyList)
                                // }
                            }
                        }
                    }
                }
        }
    }

    fun onItemSelected(id: Int) {
        viewModelScope.launch {
            state.emit(State.NavigateToDetail(id))
        }
    }

    fun filterByName(text: String) {
        viewModelScope.launch {
            state.emit(State.FilteredListByName(comicList.filter { comic ->
                comic.title.lowercase().contains(text.lowercase())
            }))
        }
    }
}

// Different possible states
sealed class State {
    data object Loading : State()
    //data object EmptyList : State()
    data class Error(val error: Failure) : State()
    data class ListReceived(val listComicModels: List<ComicModel>?) : State()
    data class FilteredListByName(val filteredComicList: List<ComicModel>?) : State()
    data class NavigateToDetail(val comicId: Int) : State()
}
