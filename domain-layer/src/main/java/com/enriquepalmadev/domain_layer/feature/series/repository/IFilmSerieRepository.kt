package com.enriquepalmadev.domain_layer.feature.series.repository

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain

interface IFilmSerieRepository {
    suspend fun getListOfAllSeries(): Either<FailureDomain, List<FilmSerieModel>?>
    suspend fun getSerieById(id: Int): Either<FailureDomain, FilmSerieModel>
}