package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListOfAllSeriesUseCase @Inject constructor(
    private val filmSerieRepository: IFilmSerieRepository
) {
    suspend fun getListOfAllSeries(): Flow<Either<FailureDomain, List<FilmSerieModel>?>> {
        return flow { emit(
            when(val responseEither = filmSerieRepository.getListOfAllSeries()){
                is Either.Error -> Either.Error(error = responseEither.error)
                is Either.Success -> Either.Success(data = responseEither.data?.filter {
                    // This was in viewmodel but I changed it
                    it.description.isNullOrEmpty().not()
                }?.sortedByDescending { it.startYear })
            }
        ) }
    }
}