package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListFilterByNameUseCase @Inject constructor(
    private val filmSerieRepository: IFilmSerieRepository
) {
    // private val filmSerieRepository = FilmSerieRepositoryImpl()
    suspend fun getListFilterByName(
        newText: String,
        series: List<FilmSerieModel>
    ): Flow<ResponseEither<FailureDomain, List<FilmSerieModel>>> {
        return flow { emit(filmSerieRepository.filterByName(newText, series)) }
    }
}