package com.enriquepalmadev.appmarvel.domain.feature.series.usecase

import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.data.feature.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchListFilterByNameUseCase @Inject constructor(
    private val filmSerieRepository : FilmSerieRepositoryImpl
){
    // private val filmSerieRepository = FilmSerieRepositoryImpl()
    suspend fun getListFilterByName(
        newText: String,
        series: List<FilmSerieModel>
    ): Flow<ResponseEither<Failure, List<FilmSerieModel>>> {
        return flow { emit(filmSerieRepository.filterByName(newText, series)) }
    }
}