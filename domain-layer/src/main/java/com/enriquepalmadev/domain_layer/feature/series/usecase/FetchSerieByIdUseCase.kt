package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchSerieByIdUseCase @Inject constructor(
    private val filmSerieRepository: IFilmSerieRepository
) {
    suspend fun getSerieById(id: Int): Flow<Either<FailureDomain, FilmSerieModel>> {
        return flow { emit(filmSerieRepository.getSerieById(id)) }
    }
}