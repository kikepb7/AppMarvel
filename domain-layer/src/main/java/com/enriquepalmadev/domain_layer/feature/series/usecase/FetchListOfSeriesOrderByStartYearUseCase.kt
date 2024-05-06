package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.utils.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEitherDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListOfSeriesOrderByStartYearUseCase @Inject constructor(
    private val filmSerieRepository: IFilmSerieRepository
) {
    // private val filmSerieRepository = FilmSerieRepositoryImpl()
    suspend fun getListOfSeriesOrderByStartYear(series: List<FilmSerieModel>): Flow<ResponseEitherDomain<FailureDomain, List<FilmSerieModel>>> {
        return flow { emit(filmSerieRepository.orderListByStartYear(series)) }
    }
}