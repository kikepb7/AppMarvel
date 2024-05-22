package com.enriquepalmadev.domain_layer.feature.series.repository

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel

interface ILocalSerieRepository {
    suspend fun getAllSeries(): List<FilmSerieModel>
    suspend fun getSerieById(id : Int): FilmSerieModel
    suspend fun updateFavSerie(id : Boolean)
}