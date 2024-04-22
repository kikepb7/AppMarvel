package com.enriquepalmadev.appmarvel.domain.series.usecase

import com.enriquepalmadev.appmarvel.data.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.data.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchListOfSeriesOrderByStartYearUseCase {

    private val filmSerieRepository = FilmSerieRepositoryImpl()

    suspend fun getListOfSeriesOrderByStartYear(series: List<FilmSerieModel>): Flow<ResponseEither<Failure, List<FilmSerieModel>>> {
        return flow { emit(filmSerieRepository.orderListByStartYear(series)) }
    }
}