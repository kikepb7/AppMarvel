package com.enriquepalmadev.appmarvel.domain

import com.enriquepalmadev.appmarvel.domain.model.ComicModel

interface ComicRepository {

    suspend fun fetchComicList() : List<ComicModel>
    suspend fun fetchComicDetail(comicId : String) : ComicModel?
}