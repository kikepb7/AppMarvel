package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalSerieByIdUseCase @Inject constructor(
    private val localSerieRepository: ILocalSerieRepository
) {
    suspend fun getLocalSerieById(id : Int): Flow<FilmSerieModel> {
        return flow { emit( localSerieRepository.getSerieById(id) ) }
    }
}