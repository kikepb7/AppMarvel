package com.enriquepalmadev.data_layer.feature.series.api.datasource

import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.data_layer.feature.series.failure.FailureData
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither

interface ISerieDataSource {
    suspend fun getListOfAllSeries() : ResponseEither<FailureData, ObjectResponseDto?>
    suspend fun getSerieById(id: Int) : ResponseEither<FailureData, MarvelFilmSerieItemDto>
}