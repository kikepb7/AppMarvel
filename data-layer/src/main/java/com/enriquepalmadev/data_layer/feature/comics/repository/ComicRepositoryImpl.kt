package com.enriquepalmadev.data_layer.feature.comics.repository

import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicDatabaseDataSource
import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.data_layer.feature.comics.utils.comicModelToComicEntity
import com.enriquepalmadev.data_layer.feature.comics.utils.dtoToComicListModel
import com.enriquepalmadev.data_layer.feature.comics.utils.dtoToComicModel
import com.enriquepalmadev.data_layer.feature.comics.utils.toFailureDomain
import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val remoteDataSource: ComicRemoteDataSource,
    private val databaseDataSource: ComicDatabaseDataSource
) : ComicRepository {
    override suspend fun fetchComicList(): Either<FailureDomain, List<ComicModel>?> {

        return try {
            val comicListFromDatabase = databaseDataSource.findComicsFromDatabase()

            if (comicListFromDatabase.isNotEmpty()) {
                Either.Success(data = comicListFromDatabase)
            } else {
                when (val response = remoteDataSource.fetchComicsFromApi()) {
                    is Either.Failure -> Either.Failure(error = response.error.toFailureDomain())
                    is Either.Success -> {
                        val comicListFromApi = response.data?.data?.results?.dtoToComicListModel()

                        comicListFromApi?.let { comicListDto ->
                            databaseDataSource.clearComicList()
                            databaseDataSource.insertComicListToDatabase(comicList = comicListDto.map { comicDto ->
                                comicDto.comicModelToComicEntity()
                            })
                        }
                        Either.Success(data = comicListFromApi)
                    }
                }
            }
        } catch (e: Exception) {
            Either.Failure(error = FailureDomain.ApiError)
        }
    }

    override suspend fun fetchComicDetail(comicId: Int): Either<FailureDomain, ComicModel?> = try {

        val comicDetailFromDatabase =
            databaseDataSource.findComicDetailFromDatabase(comicId = comicId)

        if (comicDetailFromDatabase != null) {
            Either.Success(data = comicDetailFromDatabase)
        } else {
            when (val response = remoteDataSource.fetchComicDetailFromApi(comicId)) {
                is Either.Failure -> Either.Failure(error = response.error.toFailureDomain())
                is Either.Success -> {
                    val comicFromApi =
                        response.data?.data?.results?.firstOrNull()?.dtoToComicModel()

                    comicFromApi?.let { comicModel ->
                        databaseDataSource.clearComic(comicId = comicId)
                        databaseDataSource.insertComicToDatabase(comic = comicModel.comicModelToComicEntity())
                    }
                    Either.Success(data = comicFromApi)
                }
            }
        }
    } catch (e: Exception) {
        Either.Failure(error = FailureDomain.ApiError)
    }

    override suspend fun insertComicIntoDatabase(comic: ComicModel): Either<FailureDomain, Unit> = try {

        val comicFavorite = databaseDataSource.insertComicToDatabase(comic = comic.comicModelToComicEntity())


    }
}