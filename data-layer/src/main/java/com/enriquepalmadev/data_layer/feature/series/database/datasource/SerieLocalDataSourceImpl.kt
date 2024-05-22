package com.enriquepalmadev.data_layer.feature.series.database.datasource

import com.enriquepalmadev.data_layer.feature.series.database.dao.SerieDao
import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity
import javax.inject.Inject

class SerieLocalDataSourceImpl @Inject constructor(
    private val serieDao: SerieDao
): ISerieLocalDataSource {

    override suspend fun getAllSeries(): List<SerieEntity> {
        return serieDao.getAllSeries()
    }

    override suspend fun updateFavSerie(fav: Boolean) {
        return serieDao.updateFavSerie(fav)
    }

    override suspend fun getSerieById(idSerie: Int): SerieEntity {
        return serieDao.getSerieById(idSerie)
    }
}