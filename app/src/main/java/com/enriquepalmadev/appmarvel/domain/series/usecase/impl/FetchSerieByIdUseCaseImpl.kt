package com.enriquepalmadev.appmarvel.domain.series.usecase.impl

import com.enriquepalmadev.appmarvel.data.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchSerieByIdUseCaseImpl {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    suspend fun getSerieById(id: Int): Flow<FilmSerieModel> {
        return flow { emit(filmSerieRepository.getSerieById(id)) }
    }
}