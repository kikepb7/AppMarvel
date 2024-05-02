package com.enriquepalmadev.domain_layer.feature.comics

import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain

interface ComicRepository {
    suspend fun fetchComicList() : Either<FailureDomain, List<ComicModel>?>
    suspend fun fetchComicDetail(comicId : Int) : Either<FailureDomain, ComicModel?>
}