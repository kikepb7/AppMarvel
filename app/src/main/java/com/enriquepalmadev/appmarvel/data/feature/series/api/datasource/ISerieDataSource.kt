package com.enriquepalmadev.appmarvel.data.feature.series.api.datasource

import com.enriquepalmadev.appmarvel.data.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.appmarvel.data.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.ResponseEither

interface ISerieDataSource {

    suspend fun getListOfAllSeries() : ResponseEither <Failure, ObjectResponseDto?>
    suspend fun getSerieById(id: Int) : ResponseEither <Failure, MarvelFilmSerieItemDto>
}