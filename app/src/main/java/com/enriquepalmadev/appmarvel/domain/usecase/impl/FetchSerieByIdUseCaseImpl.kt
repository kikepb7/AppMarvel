package com.enriquepalmadev.appmarvel.domain.usecase.impl

import com.enriquepalmadev.appmarvel.data.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.usecase.IFetchSerieByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchSerieByIdUseCaseImpl: IFetchSerieByIdUseCase {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    override suspend fun getSerieById(id: Int): Flow<FilmSerieModel> {
        return flow { emit(filmSerieRepository.getSerieById(id)) }
    }
}