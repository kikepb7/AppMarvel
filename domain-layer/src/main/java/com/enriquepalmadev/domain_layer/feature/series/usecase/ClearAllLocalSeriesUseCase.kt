package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import javax.inject.Inject

class ClearAllLocalSeriesUseCase @Inject constructor(
    private val localSerieRepository: ILocalSerieRepository
) {
    suspend fun clearAllLocalSeries(){
        localSerieRepository.clearAllSeries()
    }
}