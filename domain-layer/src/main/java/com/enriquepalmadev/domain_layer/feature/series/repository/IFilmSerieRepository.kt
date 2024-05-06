package com.enriquepalmadev.domain_layer.feature.series.repository

import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.utils.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEitherDomain

interface IFilmSerieRepository {

    suspend fun getListOfAllSeries(): ResponseEitherDomain<FailureDomain, List<FilmSerieModel>?>
    suspend fun getSerieById(id: Int): ResponseEitherDomain<FailureDomain, FilmSerieModel>
    suspend fun orderListByStartYear(series: List<FilmSerieModel>): ResponseEitherDomain<FailureDomain, List<FilmSerieModel>>
    suspend fun orderListByAlphabet(series: List<FilmSerieModel>): ResponseEitherDomain<FailureDomain, List<FilmSerieModel>>
    suspend fun filterByName(newText: String, series: List<FilmSerieModel>) : ResponseEitherDomain<FailureDomain, List<FilmSerieModel>>
}