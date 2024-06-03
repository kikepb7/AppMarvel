package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListOfSeriesOrderByAlphabetUseCase @Inject constructor() {
    suspend fun getListOfSeriesOrderByAlphabet(series: List<FilmSerieModel>): Flow<Either<FailureDomain, List<FilmSerieModel>>> {
        return flow { emit(
            if(series.isNotEmpty()){
                Either.Success(data = series.sortedBy { it.title })
            } else {
                Either.Error(error = FailureDomain.EmptyErrorDomain)
            }
        )}
    }
}