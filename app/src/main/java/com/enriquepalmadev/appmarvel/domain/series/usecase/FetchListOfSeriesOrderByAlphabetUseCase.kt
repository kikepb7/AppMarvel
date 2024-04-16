package com.enriquepalmadev.appmarvel.domain.series.usecase

import com.enriquepalmadev.appmarvel.data.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchListOfSeriesOrderByAlphabetUseCase {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    suspend fun getListOfSeriesOrderByAlphabet(series: List<FilmSerieModel>): Flow<List<FilmSerieModel>> {
        return flow { emit(filmSerieRepository.orderListByAlphabet(series)) }
    }
}