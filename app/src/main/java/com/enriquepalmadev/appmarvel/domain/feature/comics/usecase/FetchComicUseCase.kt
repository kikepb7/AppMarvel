package com.enriquepalmadev.appmarvel.domain.feature.comics.usecase

import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.data.feature.comics.repository.ComicRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchComicUseCase {

    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicList(): Flow<Either<Failure, List<ComicModel>?>> {
        return flow { emit(comicListRepository.fetchComicList()) }
    }
}