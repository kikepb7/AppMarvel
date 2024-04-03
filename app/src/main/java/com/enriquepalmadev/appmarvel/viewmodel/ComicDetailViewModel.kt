package com.enriquepalmadev.appmarvel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.model.ComicProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComicDetailViewModel : ViewModel() {
    val state = MutableStateFlow<DetailState>(DetailState.Loading)

    fun getComicDetail(comicId: Long) {
        viewModelScope.launch {
            val comic = ComicProvider.comicsList.find {
                it.id.toInt() == comicId.toInt()
            }

            comic?.let { state.emit(DetailState.ComicDetail(it)) }
        }
    }
}

// Different possible states
sealed class DetailState {
    data object Loading : DetailState()
    data object Error : DetailState()
    data class ComicDetail(val comic: Comic) : DetailState()
}