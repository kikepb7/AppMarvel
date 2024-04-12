package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow

interface IFetchListOfSeriesOrderByStartYear {

    suspend fun getListOfSeriesOrderByStartYear(series: ArrayList<FilmSerieModel>) : Flow<ArrayList<FilmSerieModel>>
}