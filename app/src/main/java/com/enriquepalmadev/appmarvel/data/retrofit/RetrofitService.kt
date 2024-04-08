package com.enriquepalmadev.appmarvel.data.retrofit

import com.enriquepalmadev.appmarvel.data.model.ComicModelDto
import com.enriquepalmadev.appmarvel.data.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("/comics")
    suspend fun getComics(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("limit") limit: Int? = null
    ): List<ComicModelDto>

    @GET("/comics/{comicId}")
    suspend fun getComicById(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Path("comicId") comicId: Long? = null
    ): ComicModelDto
}


object Retrofit {

    fun makeRetrofitService(): RetrofitService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
}