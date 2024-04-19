package com.enriquepalmadev.appmarvel.data.feature.comics.repository

import com.enriquepalmadev.appmarvel.data.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.data.feature.comics.utils.dtoToComicListModel
import com.enriquepalmadev.appmarvel.domain.feature.comics.ComicRepository
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import java.io.IOException

class ComicRepositoryImpl : ComicRepository {

    private val remoteDataSource = ComicRemoteDataSource()

    override suspend fun fetchComicList(): Either<Failure, List<ComicModel>?> {
        val request = remoteDataSource.fetchComicsFromApi()

        return try {
            if (request.isSuccessful && request.body() != null) {
                Either.Success(data = request.body()?.data?.results?.dtoToComicListModel())
            } else {
                if (request.code() == 401) {
                    // Unauthorized error --> Error Screen
                    Either.Failure(ApiError(code = request.code(), message = "Unauthorized"))
                } else {
                    // Generic error
                    Either.Failure(ApiError(code = request.code(), message = "Request failed"))
                }
            }
        } catch (e: IOException) {
            Either.Failure(ApiError(code = request.code(), message = "Network error: ${e.message}"))
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun fetchComicDetail(comicId: Int): ComicModel? {
        try {
            val request = remoteDataSource.fetchComicDetailFromApi(comicId)

            if (request.isSuccessful) {
                return request.body()?.data?.results?.dtoToComicListModel()?.getOrNull(0)
            } else {
                throw Exception("Error: ${request.errorBody()} - Code: ${request.code()}")
            }
        } catch (e: Exception) {
            throw Exception("${e.printStackTrace()}")
        }
    }
}