package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow

interface IFetchListOfSeriesOrderByAlphabet {

    suspend fun getListOfSeriesOrderByAlphabet(series: ArrayList<FilmSerieModel>) : Flow<ArrayList<FilmSerieModel>>

}