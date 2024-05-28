package com.enriquepalmadev.domain_layer.feature.series.repository

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel

interface ILocalSerieRepository {
    suspend fun updateFavSerie(id : Int, fav: Boolean)
    suspend fun insertAllSeries(series : List<FilmSerieModel>)
    suspend fun getAllSeries(): List<FilmSerieModel>
}