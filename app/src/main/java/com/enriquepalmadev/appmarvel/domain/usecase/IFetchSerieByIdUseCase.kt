package com.enriquepalmadev.appmarvel.domain.usecase

import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow

interface IFetchSerieByIdUseCase {

    suspend fun getSerieById(id: Int) : Flow<FilmSerieModel>
}