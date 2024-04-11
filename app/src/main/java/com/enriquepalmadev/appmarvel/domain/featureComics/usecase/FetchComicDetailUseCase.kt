package com.enriquepalmadev.appmarvel.domain.featureComics.usecase

import com.enriquepalmadev.appmarvel.data.featureComics.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchComicDetailUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicDetail(comicId: Int) : Flow<ComicModel?> {
        return flow { emit(comicListRepository.fetchComicDetail(comicId)) }
    }
}