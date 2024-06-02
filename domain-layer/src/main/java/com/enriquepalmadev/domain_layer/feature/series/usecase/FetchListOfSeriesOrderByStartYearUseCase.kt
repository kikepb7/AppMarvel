package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListOfSeriesOrderByStartYearUseCase @Inject constructor() {
    suspend fun getListOfSeriesOrderByStartYear(series: List<FilmSerieModel>): Flow<Either<FailureDomain, List<FilmSerieModel>>> {
        return flow { emit(
            if (series.isNotEmpty()) {
                Either.Success(data = series.sortedByDescending { it.startYear })
            } else {
                Either.Error(error = FailureDomain.EmptyErrorDomain)
            }
        )}
    }
}