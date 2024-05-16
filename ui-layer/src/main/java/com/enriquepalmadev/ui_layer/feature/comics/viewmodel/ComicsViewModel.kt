package com.enriquepalmadev.ui_layer.feature.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.usecase.FetchComicUseCase
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenError
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenLoading
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicScreenState
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.ComicListType
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListModel
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListModelHeader
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListScreenError
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
) : ViewModel() {

    private val _state = MutableStateFlow<ComicScreenState?>(ComicScreenState())
    val state: StateFlow<ComicScreenState?> = _state.asStateFlow()

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event.asStateFlow()

    private var comicList: List<ComicModel> = emptyList()

    fun getComicsList() {
        viewModelScope.launch {
            fetchComicListUseCase.fetchComicList()
                .onStart {
                    _state.update { comicScreenState ->
                        comicScreenState?.copy(
                            loadingScreenData = ComicListScreenLoading(loader = true)
                        )
                    }
                }
                .catch { e ->
                    _state.update { comicScreenState ->
                        comicScreenState?.copy(
                            loadingScreenData = ComicListScreenLoading(loader = false),
                            errorScreenData = ComicListScreenError(
                                image = R.drawable.comic_detail_error,
                                errorMsg = e.message.toString(),
                            )
                        )
                    }
                }
                .collect { result ->
                    when (result) {
                        is Either.Failure ->
                            _state.update { comicScreenState ->
                                comicScreenState?.copy(
                                    loadingScreenData = ComicListScreenLoading(loader = false),
                                    errorScreenData = result.error.toComicListScreenError()
                                )
                            }

                        is Either.Success -> {
                            if (!result.data.isNullOrEmpty()) {
                                val filteredList = result.data?.filter { comic ->
                                    (!comic.description.isNullOrEmpty() && comic.description != "#N/A") &&
                                            (comic.thumbnail != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
                                }

                                filteredList?.let {
                                    comicList =
                                        filteredList // Guardamos la lista filtrada en una variable

                                    _state.update { comicScreenState ->
                                        comicScreenState?.copy(
                                            comicScreenData = ComicListScreenModel(
                                                comicListScreenHeader = toComicListModelHeader(),
                                                comicListModel = filteredList.toComicListModel(
                                                    ComicListType.ALL_COMICS
                                                ),
                                                favoriteListModel = filteredList.toComicListModel(
                                                    ComicListType.FAVORITES
                                                ),
                                            ),
                                            loadingScreenData = ComicListScreenLoading(loader = false)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
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

    // Different possible events
    sealed class Event {
        data class FilteredListByName(val filteredComicList: List<ComicModel>?) : Event()
        data class NavigateToDetail(val comicId: Int) : Event()
        data object NavigateToHome : Event()
        data object Idle : Event()
    }
}
