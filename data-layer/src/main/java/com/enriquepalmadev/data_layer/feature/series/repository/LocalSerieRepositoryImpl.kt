package com.enriquepalmadev.data_layer.feature.series.repository

import com.enriquepalmadev.data_layer.feature.series.utils.entityToSerieListModel
import com.enriquepalmadev.data_layer.feature.series.utils.entityToSerieModel
import com.enriquepalmadev.data_layer.feature.series.database.datasource.SerieLocalDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.utils.modelToSerieListEntity
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import javax.inject.Inject


class LocalSerieRepositoryImpl @Inject constructor(
    private val serieLocalDataSourceImpl: SerieLocalDataSourceImpl
) : ILocalSerieRepository{
    override suspend fun getAllSeries(): List<FilmSerieModel> {
        return serieLocalDataSourceImpl.getAllSeries().entityToSerieListModel()
        }
/*
    override suspend fun getSerieById(id: Int): FilmSerieModel {
        return serieLocalDataSourceImpl.getSerieById(id).entityToSerieModel()
    }

 */

    override suspend fun updateFavSerie(id: Int, fav: Boolean) {
        return serieLocalDataSourceImpl.updateFavSerie(id, fav)
    }

    override suspend fun insertAllSeries(series: List<FilmSerieModel>) {
        serieLocalDataSourceImpl.insertAllSeries(series.modelToSerieListEntity())
    }

    override suspend fun clearAllSeries() {
        serieLocalDataSourceImpl.clearAllSeries()
    }
}