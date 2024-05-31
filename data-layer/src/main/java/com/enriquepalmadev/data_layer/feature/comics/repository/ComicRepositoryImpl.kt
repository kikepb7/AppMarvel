package com.enriquepalmadev.data_layer.feature.comics.repository

import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.data_layer.feature.comics.utils.dtoToComicListModel
import com.enriquepalmadev.data_layer.feature.comics.utils.dtoToComicModel
import com.enriquepalmadev.data_layer.feature.comics.utils.toFailureDomain
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import javax.inject.Inject


class ComicRepositoryImpl @Inject constructor(
    private val remoteDataSource: ComicRemoteDataSource
) : ComicRepository {

    override suspend fun fetchComicList(): Either<FailureDomain, List<ComicModel>?> {

        return when (val response = remoteDataSource.fetchComicsFromApi()) {
            is Either.Error -> Either.Error(error = response.error.toFailureDomain())
            is Either.Success -> Either.Success(data = response.data?.data?.results?.dtoToComicListModel())
        }
    }

    override suspend fun fetchComicDetail(comicId: Int): Either<FailureDomain, ComicModel?> {

        return when (val response = remoteDataSource.fetchComicDetailFromApi(comicId)) {
            is Either.Error -> Either.Error(error = response.error.toFailureDomain())
            is Either.Success -> Either.Success(
                data = response.data?.data?.results?.firstOrNull()?.dtoToComicModel()
            )
        }
    }
}