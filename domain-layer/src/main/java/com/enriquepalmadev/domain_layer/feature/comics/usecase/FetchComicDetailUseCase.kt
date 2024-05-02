package com.enriquepalmadev.domain_layer.feature.comics.usecase


import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchComicDetailUseCase (
    private val comicListRepository: ComicRepository
) {

//    private val comicListRepository = ComicRepositoryImpl()

    suspend fun fetchComicDetail(comicId: Int): Flow<Either<FailureDomain, ComicModel?>> {
        return flow { emit(comicListRepository.fetchComicDetail(comicId)) }
    }
}