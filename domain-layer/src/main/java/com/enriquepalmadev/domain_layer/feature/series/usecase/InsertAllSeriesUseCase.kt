package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import javax.inject.Inject

class InsertAllSeriesUseCase @Inject constructor(
    private val localSerieRepository: ILocalSerieRepository
) {
    suspend fun insertAllSeries(series : List<FilmSerieModel>){
        localSerieRepository.insertAllSeries(series)
    }
}