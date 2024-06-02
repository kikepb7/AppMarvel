package com.enriquepalmadev.ui_layer.feature.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.domain_layer.commons.Either

import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import com.enriquepalmadev.domain_layer.feature.comics.usecase.FetchComicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val fetchComicListUseCase: FetchComicUseCase
): ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()
    private var comicList: List<ComicModel> = emptyList()

    fun getComicsList() {
        viewModelScope.launch {
            fetchComicListUseCase.fetchComicList()
                .onStart { _state.emit(State.Loading) }
                .catch { e ->
                    _state.emit(State.Exception(e.message.toString()))
                }
                .collect { result ->
                    when (result) {
                        is Either.Error ->
                            _state.emit(State.Error(error = result.error))

                        is Either.Success -> {
                            if (!result.data.isNullOrEmpty()) {
                                val filteredList = result.data?.filter { comic ->
                                    (!comic.description.isNullOrEmpty() && comic.description != "#N/A") &&
                                            (comic.thumbnail != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
                                }

                                filteredList?.let {
                                    comicList = filteredList // Guardamos la lista filtrada en una variable
                                    _state.emit(State.ListReceived(comicList))
                                }
                            }
                        }
                    }
                }
        }
    }

    suspend fun done(){
        _state.emit(State.ListReceived(comicList))
    }

    fun onItemSelected(id: Int) {
        viewModelScope.launch {
            _state.emit(State.NavigateToDetail(id))
        }
    }

    fun filterByName(text: String) {
        viewModelScope.launch {
            _state.emit(State.FilteredListByName(comicList.filter { comic ->
                comic.title.lowercase().contains(text.lowercase())
            }))
        }
    }
}

// Different possible states
sealed class State {
    data object Loading : State()
    data class Error(val error: FailureDomain) : State()
    data class Exception(val message: String) : State()
    data class ListReceived(val listComicModels: List<ComicModel>?) : State()
    data class FilteredListByName(val filteredComicList: List<ComicModel>?) : State()
    data class NavigateToDetail(val comicId: Int) : State()
}
