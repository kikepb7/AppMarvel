package com.enriquepalmadev.appmarvel.domain.featureComics.usecase

import com.enriquepalmadev.appmarvel.data.featureComics.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel

class FetchComicUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicList(): List<ComicModel>? {
        return comicListRepository.fetchComicList()
    }
}