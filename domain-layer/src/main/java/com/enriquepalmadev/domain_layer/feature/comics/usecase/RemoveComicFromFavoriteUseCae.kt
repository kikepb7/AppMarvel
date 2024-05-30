package com.enriquepalmadev.domain_layer.feature.comics.usecase

import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveComicFromFavoriteUseCae @Inject constructor(
    private val comicListRepository: ComicRepository
) {

    suspend fun removeComicFromFavorite(comic: ComicModel): Flow<Boolean> {
        return flow { emit(comicListRepository.removeComicFromDatabase(comic = comic)) }
    }
}