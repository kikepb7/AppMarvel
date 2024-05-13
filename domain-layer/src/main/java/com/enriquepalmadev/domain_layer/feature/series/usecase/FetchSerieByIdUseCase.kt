package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchSerieByIdUseCase @Inject constructor(
    private val filmSerieRepository: IFilmSerieRepository
) {
    // private val filmSerieRepository = FilmSerieRepositoryImpl()
    suspend fun getSerieById(id: Int): Flow<ResponseEither<FailureDomain, FilmSerieModel>> {
        return flow { emit(filmSerieRepository.getSerieById(id)) }
    }
}