package com.enriquepalmadev.appmarvel.data.feature.comics.datasource

import com.enriquepalmadev.appmarvel.data.feature.comics.Retrofit
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ResponseMarvelDto
import com.enriquepalmadev.appmarvel.data.feature.comics.service.ComicService
import retrofit2.Response

class ComicRemoteDataSource {

    private val retrofitService: ComicService by lazy {
        Retrofit.retrofitConnection().create(ComicService::class.java)
    }

    suspend fun fetchComicsFromApi(): Response<ResponseMarvelDto> {
        return retrofitService.getComics(limit = 100)
    }

    suspend fun fetchComicDetailFromApi(comicId: Int): Response<ResponseMarvelDto> {
        return retrofitService.getComicById(comicId = comicId)
    }
}