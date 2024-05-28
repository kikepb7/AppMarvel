package com.enriquepalmadev.data_layer.feature.series.database.datasource

import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity

interface ISerieLocalDataSource {
    suspend fun updateFavSerie(idSerie: Int, fav: Boolean)
    suspend fun insertAllSeries(series : List<SerieEntity>)
    suspend fun getAllSeries() : List<SerieEntity>
}