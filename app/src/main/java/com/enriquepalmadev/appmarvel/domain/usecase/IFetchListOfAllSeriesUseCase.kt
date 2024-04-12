package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow

interface IFetchListOfAllSeriesUseCase {

    suspend fun getListOfAllSeries() : Flow<ArrayList<FilmSerieModel>>
}