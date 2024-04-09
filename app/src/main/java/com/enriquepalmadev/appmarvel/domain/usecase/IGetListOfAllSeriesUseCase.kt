package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.domain.models.FilmSerie

interface IGetListOfAllSeriesUseCase {

    suspend fun getListOfAllSeries() : ArrayList<FilmSerie>
}