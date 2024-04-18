package com.enriquepalmadev.appmarvel.data.featureComics.datasource

import com.enriquepalmadev.appmarvel.data.featureComics.Retrofit
import com.enriquepalmadev.appmarvel.data.featureComics.dto.ResponseMarvelDto
import com.enriquepalmadev.appmarvel.data.featureComics.utils.Constants
import retrofit2.Response

class ComicRemoteDataSource {

    private val retrofit = Retrofit.retrofitService

    suspend fun fetchComicsFromApi(): Response<ResponseMarvelDto> {
        return retrofit.getComics(hash = Constants.HASH, ts = Constants.TS, limit = 100)
    }

    suspend fun fetchComicDetailFromApi(comicId: Int): Response<ResponseMarvelDto> {
        return retrofit.getComicById(comicId = comicId, hash = Constants.HASH, ts = Constants.TS)
    }
}