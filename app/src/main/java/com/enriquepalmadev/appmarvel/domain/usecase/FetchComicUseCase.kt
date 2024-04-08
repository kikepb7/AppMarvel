package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.data.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.ComicRepository
import com.enriquepalmadev.appmarvel.domain.model.ComicModel

class FetchComicUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicList(): List<ComicModel> {
        return comicListRepository.fetchComicList()
    }
}