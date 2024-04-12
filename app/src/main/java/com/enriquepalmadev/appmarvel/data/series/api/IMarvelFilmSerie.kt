package com.enriquepalmadev.appmarvel.data.series.api

import com.enriquepalmadev.appmarvel.data.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.appmarvel.data.series.api.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMarvelFilmSerie {

    @GET("series")
    suspend fun getListOfAllSeries(
        @Query("ts")ts: String = Constants.TIMESTAMP,
        @Query("apikey")apikey: String = Constants.API_KEY,
        @Query("hash")hash: String = Constants.hash(),
        @Query("limit")limit: Int = Constants.LIMIT
    ): Response <ObjectResponseDto>

    @GET("series/{id}")
    suspend fun getSerieById(
        @Path("id")id: Int,
        @Query("ts")ts: String = Constants.TIMESTAMP,
        @Query("apikey")apikey: String = Constants.API_KEY,
        @Query("hash")hash: String = Constants.hash()
    ): Response <ObjectResponseDto>
}