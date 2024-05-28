package com.enriquepalmadev.domain_layer.feature.comics.repository

import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain

class FakeComicRepository : ComicRepository {

    private var returnError = false
    private var fakeComicList: List<ComicModel>? = null
    private var fakeComicDetail: ComicModel? = null

    override suspend fun fetchComicList(): Either<FailureDomain, List<ComicModel>?> {
        return if (returnError) {
            Either.Failure(FailureDomain.ApiError)
        } else {
            Either.Success(fakeComicList)
        }
    }

    override suspend fun fetchComicDetail(comicId: Int): Either<FailureDomain, ComicModel?> {
        return if (returnError) {
            Either.Failure(FailureDomain.ApiError)
        } else {
            Either.Success(fakeComicDetail)
        }
    }
}