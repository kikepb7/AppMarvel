package com.enriquepalmadev.ui_layer.feature.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import com.enriquepalmadev.domain_layer.feature.comics.usecase.FetchComicUseCase
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenLoading
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicScreenState
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.ComicListType
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListModel
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListModelHeader
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListScreenError
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toEmptyListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val fetchComicListUseCase: FetchComicUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ComicScreenState())
    val state: StateFlow<ComicScreenState?> = _state.asStateFlow()

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event.asStateFlow()

    private var comicList: List<ComicModel> = emptyList()

    fun getComicsList() {
        viewModelScope.launch {
            fetchComicListUseCase.fetchComicList()
                .onStart { manageLoading() }
                .catch { throwableError -> manageError(throwableError = throwableError) }
                .collect { result ->
                    when (result) {
                        is Either.Error -> { manageFailure(error = result.error) }

                        is Either.Success -> { manageSuccess(result.data) }
                    }
                }
        }
    }

    private fun manageLoading() {
        _state.update { comicScreenState ->
            comicScreenState.copy(
                loadingScreenData = ComicListScreenLoading(loader = true)
            )
        }
    }

    private fun manageError(throwableError: Throwable) {
        _state.update { comicScreenState ->
            comicScreenState.copy(
                errorScreenData = throwableError.toComicListScreenError(),
                loadingScreenData = ComicListScreenLoading(loader = false)
            )
        }
    }

    private fun manageFailure(error: FailureDomain) {
        _state.update { comicScreenState ->
            comicScreenState.copy(
                errorScreenData = error.toComicListScreenError(),
                loadingScreenData = ComicListScreenLoading(loader = false)
            )
        }
    }

    private fun manageSuccess(data: List<ComicModel>?) {
        if (!data.isNullOrEmpty()) {
            val filteredList = filterComics(data)
            updateListComicsState(filteredList = filteredList)
        } else {
            manageEmptyList()
        }
    }

    private fun filterComics(comics: List<ComicModel>): List<ComicModel> {
        return comics.filter { comic ->
            (!comic.description.isNullOrEmpty() && comic.description != "#N/A") &&
                    (comic.thumbnail != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
        }
    }

    private fun updateListComicsState(filteredList: List<ComicModel>) {
        comicList = filteredList    // TODO --> Eliminar cuando recuperemos la lista por bbdd

        _state.update { comicScreenState ->
            comicScreenState.copy(
                comicScreenData = ComicListScreenModel(
                    comicListScreenHeader = toComicListModelHeader(),
                    comicListModel = filteredList.toComicListModel(ComicListType.ALL_COMICS),
                    favoriteListModel = filteredList.toComicListModel(ComicListType.FAVORITES)
                ),
                loadingScreenData = ComicListScreenLoading(loader = false)
            )
        }
    }

    private fun manageEmptyList() {
        _state.update { comicScreenState ->
            comicScreenState.copy(
                emptyListScreenData = toEmptyListModel(),
                loadingScreenData = ComicListScreenLoading(loader = false)
            )
        }
    }

    fun filterComicsByName(text: String) {
        val filteredList = comicList.filter { comic ->
            comic.title.contains(text, ignoreCase = true)
        }
        _state.update { comicScreenState ->
            comicScreenState.copy(
                comicScreenData = comicScreenState.comicScreenData?.copy(
                    comicListModel = filteredList.toComicListModel(ComicListType.ALL_COMICS),
                    favoriteListModel = filteredList.toComicListModel(ComicListType.FAVORITES)
                )
            )
        }
    }

    fun navigateToComicDetail(comicId: Int) {
        viewModelScope.launch {
            _event.emit(Event.NavigateToDetail(comicId = comicId))
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _event.emit(Event.NavigateToHome)
        }
    }

    fun idle() {
        viewModelScope.launch {
            _event.emit(Event.Idle)
        }
    }
}

// Different possible events
sealed class Event {
    data class NavigateToDetail(val comicId: Int) : Event()
    data object NavigateToHome : Event()
    data object Idle : Event()
}
