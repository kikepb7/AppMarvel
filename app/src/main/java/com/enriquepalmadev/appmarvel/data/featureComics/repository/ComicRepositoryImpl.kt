package com.enriquepalmadev.appmarvel.data.featureComics.repository

import com.enriquepalmadev.appmarvel.data.featureComics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.appmarvel.data.featureComics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.featureComics.dto.BadRequest
import com.enriquepalmadev.appmarvel.data.featureComics.dto.Either
import com.enriquepalmadev.appmarvel.data.featureComics.dto.Failure
import com.enriquepalmadev.appmarvel.data.featureComics.dto.Unauthorized
import com.enriquepalmadev.appmarvel.data.featureComics.utils.dtoToComicListModel
import com.enriquepalmadev.appmarvel.domain.featureComics.ComicRepository
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import retrofit2.HttpException
import java.io.IOException

class ComicRepositoryImpl : ComicRepository {

    private val remoteDataSource = ComicRemoteDataSource()

    override suspend fun fetchComicList(): Either<Failure, List<ComicModel>?> {
        val request = remoteDataSource.fetchComicsFromApi()

        return try {
            if (request.isSuccessful) {
                Either.Success(data = request.body()?.data?.results?.dtoToComicListModel())
            } else {
                if (request.code() == 400) {
                 Either.Failure(BadRequest)
                } else if (request.code() == 401) {
                    Either.Failure(Unauthorized)
                }
                Either.Failure(ApiError(code = request.code(), message = request.errorBody().toString()))
            }
        /*} catch (e: IOException) {
            Either.Failure(ApiError(code = 0, message = "Connection Error"))
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Either.Failure(ApiError(code = 400, message = "Bad request"))
                401 -> Either.Failure(ApiError(code = 401, message = "Unauthorized"))
                else -> Either.Failure(ApiError(code = e.code(), message = "Server error"))
            }*/
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