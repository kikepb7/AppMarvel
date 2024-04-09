package com.enriquepalmadev.appmarvel.data.featureComics

import com.enriquepalmadev.appmarvel.data.featureComics.model.ResponseMarvelDto
import com.enriquepalmadev.appmarvel.data.featureComics.model.ResultDto

interface ComicDataSource {
    suspend fun fetchComicsFromApi(): ResponseMarvelDto<ResultDto>
    suspend fun fetchComicDetailFromApi(comicId: Int): ResponseMarvelDto<ResultDto>
}