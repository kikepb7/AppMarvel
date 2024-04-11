package com.enriquepalmadev.appmarvel.data.featureComics.datasource

import com.enriquepalmadev.appmarvel.data.featureComics.ComicDataSource
import com.enriquepalmadev.appmarvel.data.featureComics.Retrofit
import com.enriquepalmadev.appmarvel.data.featureComics.model.ResponseMarvelDto
import com.enriquepalmadev.appmarvel.data.featureComics.model.ResultDto
import com.enriquepalmadev.appmarvel.data.featureComics.utils.Constants

class ComicRemoteDataSource : ComicDataSource {

    private val retrofit = Retrofit.retrofitConection()

    override suspend fun fetchComicsFromApi(): ResponseMarvelDto<ResultDto> {
        return retrofit.getComics(hash = Constants.HASH, ts = Constants.TS, limit = 100)
    }

    override suspend fun fetchComicDetailFromApi(comicId: Int): ResponseMarvelDto<ResultDto> {
        return retrofit.getComicById(comicId = comicId, hash = Constants.HASH, ts = Constants.TS)
    }
}