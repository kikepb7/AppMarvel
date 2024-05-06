package com.enriquepalmadev.data_layer.feature.series.api.service

import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMarvelFilmSerieService {

    @GET("series")
    suspend fun getListOfAllSeries(
        @Query("limit")limit: Int = Constants.LIMIT
    ): Response<ObjectResponseDto>

    @GET("series/{id}")
    suspend fun getSerieById(
        @Path("id")id: Int
    ): Response <ObjectResponseDto>
}