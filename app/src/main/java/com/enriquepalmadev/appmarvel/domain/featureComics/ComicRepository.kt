package com.enriquepalmadev.appmarvel.domain.featureComics

import com.enriquepalmadev.appmarvel.data.featureComics.dto.Either
import com.enriquepalmadev.appmarvel.data.featureComics.dto.Failure
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel

interface ComicRepository {
    suspend fun fetchComicList() : Either<Failure, List<ComicModel>?>
    suspend fun fetchComicDetail(comicId : Int) : ComicModel?
}