package com.enriquepalmadev.appmarvel.domain.featureComics.usecase

import com.enriquepalmadev.appmarvel.data.featureComics.dto.Either
import com.enriquepalmadev.appmarvel.data.featureComics.dto.Failure
import com.enriquepalmadev.appmarvel.data.featureComics.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchComicUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicList(): Flow<Either<Failure, List<ComicModel>?>> {
        return flow { emit(comicListRepository.fetchComicList()) }
    }
}