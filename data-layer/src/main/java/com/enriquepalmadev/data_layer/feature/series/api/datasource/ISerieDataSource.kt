package com.enriquepalmadev.data_layer.feature.series.api.datasource

import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.data_layer.feature.series.api.utils.Failure
import com.enriquepalmadev.data_layer.feature.series.api.utils.ResponseEither
import com.enriquepalmadev.domain_layer.feature.series.utils.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEitherDomain

interface ISerieDataSource {

    suspend fun getListOfAllSeries() : ResponseEitherDomain<FailureDomain, ObjectResponseDto?>
    suspend fun getSerieById(id: Int) : ResponseEitherDomain<FailureDomain, MarvelFilmSerieItemDto>
}