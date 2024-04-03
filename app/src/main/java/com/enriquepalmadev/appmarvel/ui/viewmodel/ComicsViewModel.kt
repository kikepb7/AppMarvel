package com.enriquepalmadev.appmarvel.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enriquepalmadev.appmarvel.domain.model.ComicProvider
import com.enriquepalmadev.appmarvel.domain.model.ComicsModel

class ComicsViewModel : ViewModel() {

    // Encapsulates the model in a LiveData
    val imageModel = MutableLiveData<ComicsModel>()

    fun randomImage() {
        val currentImageComic = ComicProvider.random()

        imageModel.postValue(currentImageComic)
    }

    fun nextComic() {
        val currentComic = ComicProvider.getNextComic()

        imageModel.postValue(currentComic)
    }
}