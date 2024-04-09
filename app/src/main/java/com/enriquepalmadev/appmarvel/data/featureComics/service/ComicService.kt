package com.enriquepalmadev.appmarvel.data.featureComics.service

import com.enriquepalmadev.appmarvel.data.featureComics.model.ResponseMarvelDto
import com.enriquepalmadev.appmarvel.data.featureComics.model.ResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicService {

    @GET("comics")
    suspend fun getComics(
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("limit") limit: Int? = null
    ): ResponseMarvelDto<ResultDto>

    @GET("comics/{comicId}")
    suspend fun getComicById(
        @Path("comicId") comicId: Int? = null,
        @Query("hash") hash: String,
        @Query("ts") ts: String
    ): ResponseMarvelDto<ResultDto>
}