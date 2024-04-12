package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow

interface IFetchListFilterByNameUseCase {

    suspend fun getListFilterByName(newText: String, series: ArrayList<FilmSerieModel>) : Flow<ArrayList<FilmSerieModel>>
}