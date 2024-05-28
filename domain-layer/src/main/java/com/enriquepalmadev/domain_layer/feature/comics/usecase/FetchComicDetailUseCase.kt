package com.enriquepalmadev.domain_layer.feature.comics.usecase


import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchComicDetailUseCase @Inject constructor(
    private val comicListRepository: ComicRepository
) {

    suspend fun fetchComicDetail(comicId: Int): Flow<Either<FailureDomain, ComicModel?>> {
        return flow { emit(comicListRepository.fetchComicDetail(comicId)) }
    }
}