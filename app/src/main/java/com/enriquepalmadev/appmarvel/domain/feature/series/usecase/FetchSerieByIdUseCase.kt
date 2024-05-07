package com.enriquepalmadev.appmarvel.domain.feature.series.usecase

import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.data.feature.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchSerieByIdUseCase {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    suspend fun getSerieById(id: Int): Flow<ResponseEither<Failure, FilmSerieModel>> {
        return flow { emit(filmSerieRepository.getSerieById(id)) }
    }
}