package com.enriquepalmadev.appmarvel.data.api

import com.enriquepalmadev.appmarvel.data.api.dtos.MarvelFilmSerieDto
import com.enriquepalmadev.appmarvel.data.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface IMarvelFilmSerie {

    @GET("/v1/public/series")
    suspend fun getAllSeries(
        @Query("apikey")apikey: String = Constants.API_KEY,
        @Query("ts")ts: String = Constants.timeStamp,
        @Query("hash")hash: String = Constants.hash(),
        @Query("offset")offset: String
    ): MarvelFilmSerieDto
}