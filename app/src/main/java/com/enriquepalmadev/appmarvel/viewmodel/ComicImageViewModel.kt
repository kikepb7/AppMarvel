package com.enriquepalmadev.appmarvel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enriquepalmadev.appmarvel.model.ComicProvider
import com.enriquepalmadev.appmarvel.model.ComicsModel

class ComicImageViewModel : ViewModel() {

    // Encapsula el modelo en un LiveData
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