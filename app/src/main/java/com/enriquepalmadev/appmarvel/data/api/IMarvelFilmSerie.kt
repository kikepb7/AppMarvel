package com.enriquepalmadev.appmarvel.data.api

import com.enriquepalmadev.appmarvel.data.api.dtos.MarvelFilmSerieDto
import com.enriquepalmadev.appmarvel.data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface IMarvelFilmSerie {

    @GET
    suspend fun getListOfAllSeries(
        @Url url: String,
        @Query("ts")ts: String = Constants.TIMESTAMP,
        @Query("apikey")apikey: String = Constants.API_KEY,
        @Query("hash")hash: String = Constants.hash()
    ): Response <MarvelFilmSerieDto>
}