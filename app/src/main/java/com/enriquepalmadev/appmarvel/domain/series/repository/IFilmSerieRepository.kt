package com.enriquepalmadev.appmarvel.domain.series.repository

import com.enriquepalmadev.appmarvel.data.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.data.series.api.utils.GenericException
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel

interface IFilmSerieRepository {

    suspend fun getListOfAllSeries(): ResponseEither<Failure, List<FilmSerieModel>?>
    suspend fun getSerieById(id: Int): FilmSerieModel
    suspend fun orderListByStartYear(series: List<FilmSerieModel>): List<FilmSerieModel>
    suspend fun orderListByAlphabet(series: List<FilmSerieModel>): List<FilmSerieModel>
    suspend fun filterByName(newText: String, series: List<FilmSerieModel>) : List<FilmSerieModel>
}