package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.data.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.model.ComicModel

class FetchComicDetailUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicDetail(comicId: String) : ComicModel? {
        return comicListRepository.fetchComicDetail(comicId)
    }
}