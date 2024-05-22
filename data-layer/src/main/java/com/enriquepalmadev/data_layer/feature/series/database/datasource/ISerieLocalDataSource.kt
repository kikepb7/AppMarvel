package com.enriquepalmadev.data_layer.feature.series.database.datasource

import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity

interface ISerieLocalDataSource {

    suspend fun getAllSeries(): List<SerieEntity>
    suspend fun updateFavSerie(fav : Boolean)
    suspend fun getSerieById(idSerie : Int): SerieEntity
    suspend fun insertAllSeries(series : List<SerieEntity>)
}