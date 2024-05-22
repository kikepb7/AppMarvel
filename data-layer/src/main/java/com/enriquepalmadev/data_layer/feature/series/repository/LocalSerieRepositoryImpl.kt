package com.enriquepalmadev.data_layer.feature.series.repository

import com.enriquepalmadev.data_layer.feature.series.api.utils.entityToSerieListModel
import com.enriquepalmadev.data_layer.feature.series.api.utils.entityToSerieModel
import com.enriquepalmadev.data_layer.feature.series.database.datasource.SerieLocalDataSourceImpl
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import javax.inject.Inject


class LocalSerieRepositoryImpl @Inject constructor(
    private val serieLocalDataSourceImpl: SerieLocalDataSourceImpl
) : ILocalSerieRepository{
    override suspend fun getAllSeries(): List<FilmSerieModel> {
        return serieLocalDataSourceImpl.getAllSeries().entityToSerieListModel()
        }

    override suspend fun getSerieById(id: Int): FilmSerieModel {
        return serieLocalDataSourceImpl.getSerieById(id).entityToSerieModel()
    }

    override suspend fun updateFavSerie(id: Boolean) {
        return serieLocalDataSourceImpl.updateFavSerie(id)
    }
}