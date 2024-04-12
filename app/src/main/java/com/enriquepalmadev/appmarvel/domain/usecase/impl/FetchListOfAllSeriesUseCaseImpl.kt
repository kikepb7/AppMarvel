package com.enriquepalmadev.appmarvel.domain.usecase.impl

import com.enriquepalmadev.appmarvel.data.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.usecase.IFetchListOfAllSeriesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchListOfAllSeriesUseCaseImpl : IFetchListOfAllSeriesUseCase {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    override suspend fun getListOfAllSeries(): Flow<ArrayList<FilmSerieModel>> {
        return flow { emit(filmSerieRepository.getListOfAllSeries()) }
    }
}