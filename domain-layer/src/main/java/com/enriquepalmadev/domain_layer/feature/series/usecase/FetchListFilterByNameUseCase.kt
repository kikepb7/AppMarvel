package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListFilterByNameUseCase @Inject constructor() {
    suspend fun getListFilterByName(
        newText: String,
        series: List<FilmSerieModel>
    ): Flow<Either<FailureDomain, List<FilmSerieModel>>> {
        return flow { emit(
            if (series.isNotEmpty()) {
                Either.Success(data = series.filter {
                    it.title.lowercase().contains(newText.lowercase())
                })
            } else {
                Either.Error(error = FailureDomain.EmptyErrorDomain)
            }
        )}
    }
}