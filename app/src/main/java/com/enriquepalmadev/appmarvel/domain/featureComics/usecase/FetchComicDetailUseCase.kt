package com.enriquepalmadev.appmarvel.domain.featureComics.usecase

import com.enriquepalmadev.appmarvel.data.featureComics.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel

class FetchComicDetailUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicDetail(comicId: Int) : ComicModel? {
        return comicListRepository.fetchComicDetail(comicId)
    }
}