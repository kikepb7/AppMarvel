package com.enriquepalmadev.appmarvel.domain.feature.comics

import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel

interface ComicRepository {
    suspend fun fetchComicList() : Either<Failure, List<ComicModel>?>
    suspend fun fetchComicDetail(comicId : Int) : ComicModel?
    suspend fun fetchComicsFilteredByName(text: String) : List<ComicModel>?
}