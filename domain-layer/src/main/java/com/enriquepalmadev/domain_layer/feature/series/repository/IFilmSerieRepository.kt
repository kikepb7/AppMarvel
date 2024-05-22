package com.enriquepalmadev.domain_layer.feature.series.repository

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither

interface IFilmSerieRepository {
    suspend fun getListOfAllSeries(): ResponseEither<FailureDomain, List<FilmSerieModel>?>
    suspend fun getSerieById(id: Int): ResponseEither<FailureDomain, FilmSerieModel>
}