package com.enriquepalmadev.appmarvel.domain.usecase.impl

import com.enriquepalmadev.appmarvel.data.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.usecase.IFetchListOfSeriesOrderByStartYear
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchListOfSeriesOrderByStartYearUseCaseImpl: IFetchListOfSeriesOrderByStartYear {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    override suspend fun getListOfSeriesOrderByStartYear(series: ArrayList<FilmSerieModel>): Flow<ArrayList<FilmSerieModel>> {
        return flow { emit(filmSerieRepository.orderListByStartYear(series)) }
    }
}