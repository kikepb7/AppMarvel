package com.enriquepalmadev.data_layer.feature.comics.service

import com.enriquepalmadev.data_layer.feature.comics.dto.ResponseMarvelDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicService {

    @GET("comics")
    suspend fun getComics(
        @Query("limit") limit: Int? = null
    ): Response<ResponseMarvelDto>

    @GET("comics/{comicId}")
    suspend fun getComicById(
        @Path("comicId") comicId: Int? = null,
    ): Response<ResponseMarvelDto>
}