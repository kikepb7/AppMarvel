package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalSeriesListUseCase @Inject constructor(
    private val localSerieRepository: ILocalSerieRepository
) {
    suspend fun getLocalSeriesList(): Flow<List<FilmSerieModel>?> {
        return flow { emit( localSerieRepository.getAllSeries() ) }
    }
}