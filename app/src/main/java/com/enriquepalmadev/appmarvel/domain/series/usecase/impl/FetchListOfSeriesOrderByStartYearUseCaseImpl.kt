package com.enriquepalmadev.appmarvel.domain.series.usecase.impl

import com.enriquepalmadev.appmarvel.data.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchListOfSeriesOrderByStartYearUseCaseImpl {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    suspend fun getListOfSeriesOrderByStartYear(series: ArrayList<FilmSerieModel>): Flow<ArrayList<FilmSerieModel>> {
        return flow { emit(filmSerieRepository.orderListByStartYear(series)) }
    }
}