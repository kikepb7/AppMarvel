package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListOfAllSeriesUseCase @Inject constructor(
    private val filmSerieRepository: IFilmSerieRepository
) {
    suspend fun getListOfAllSeries(): Flow<ResponseEither<FailureDomain, List<FilmSerieModel>?>> {
        return flow { emit(
            when(val responseEither = filmSerieRepository.getListOfAllSeries()){
                is ResponseEither.Failure -> ResponseEither.Failure(failure = responseEither.failure)
                is ResponseEither.Success -> ResponseEither.Success(success = responseEither.success?.filter {
                    // This was in viewmodel but I changed it
                    it.description.isNullOrEmpty().not()
                }?.sortedByDescending { it.startYear })
            }
        ) }
    }
}