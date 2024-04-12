package com.enriquepalmadev.appmarvel.domain.series.repository

import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel

interface IFilmSerieRepository {

    suspend fun getListOfAllSeries(): ArrayList<FilmSerieModel>
    suspend fun getSerieById(id: Int): FilmSerieModel
    suspend fun orderListByStartYear(series: ArrayList<FilmSerieModel>): ArrayList<FilmSerieModel>
    suspend fun orderListByAlphabet(series: ArrayList<FilmSerieModel>): ArrayList<FilmSerieModel>
    suspend fun filterByName(newText: String, series: ArrayList<FilmSerieModel>) : ArrayList<FilmSerieModel>
}