package com.enriquepalmadev.appmarvel.data.feature.comics.repository

import com.enriquepalmadev.appmarvel.data.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.data.feature.comics.utils.dtoToComicListModel
import com.enriquepalmadev.appmarvel.data.feature.comics.utils.dtoToComicModel
import com.enriquepalmadev.appmarvel.domain.feature.comics.ComicRepository
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel

class ComicRepositoryImpl : ComicRepository {

    private val remoteDataSource = ComicRemoteDataSource()

    override suspend fun fetchComicList(): Either<Failure, List<ComicModel>?> {

        return when (val response = remoteDataSource.fetchComicsFromApi()) {
            is Either.Failure -> Either.Failure(error = response.error)
            is Either.Success -> Either.Success(data = response.data?.data?.results?.dtoToComicListModel())
        }
    }

    override suspend fun fetchComicDetail(comicId: Int): Either<Failure, ComicModel?> {

        return when (val response = remoteDataSource.fetchComicDetailFromApi(comicId)) {
            is Either.Failure -> Either.Failure(error = response.error)
            is Either.Success -> Either.Success(
                data = response.data?.data?.results?.getOrNull(0)?.dtoToComicModel()
            )
        }
    }
}