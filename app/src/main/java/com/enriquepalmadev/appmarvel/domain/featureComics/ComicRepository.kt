package com.enriquepalmadev.appmarvel.domain.featureComics

import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel

interface ComicRepository {

    suspend fun fetchComicList() : List<ComicModel>?
    suspend fun fetchComicDetail(comicId : Int) : ComicModel?
}