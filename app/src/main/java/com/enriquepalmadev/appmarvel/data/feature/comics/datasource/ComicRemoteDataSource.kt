package com.enriquepalmadev.appmarvel.data.feature.comics.datasource

import com.enriquepalmadev.appmarvel.data.feature.comics.Retrofit
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ResponseMarvelDto
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Unauthorized
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.UnknownHostError
import com.enriquepalmadev.appmarvel.data.feature.comics.service.ComicService
import java.io.IOException

class ComicRemoteDataSource {

    private val retrofitService: ComicService by lazy {
        Retrofit.retrofitConnection().create(ComicService::class.java)
    }

    suspend fun fetchComicsFromApi(): Either<Failure, ResponseMarvelDto?> {
        val request = retrofitService.getComics(limit = 100)

        return try {
            if (request.isSuccessful && request.body() != null) {
                Either.Success(data = request.body())
            } else {
                if (request.code() == 400) {
                    Either.Failure(error = UnknownHostError)
                } else if (request.code() == 401) {
                    Either.Failure(error = Unauthorized)
                } else {
                    Either.Failure(
                        ApiError(
                            code = request.code(),
                            message = request.errorBody().toString()
                        )
                    )
                }
            }
        } catch (e: IOException) {
            Either.Failure(
                ApiError(
                    code = request.code(),
                    message = request.errorBody().toString()
                )
            )
        } catch (e: Exception) {
            Either.Failure(
                ApiError(
                    code = request.code(),
                    message = request.errorBody().toString()
                )
            )
        }
    }

    suspend fun fetchComicDetailFromApi(comicId: Int): Either<Failure, ResponseMarvelDto?> {
        val request = retrofitService.getComicById(comicId = comicId)

        return try {
            if (request.isSuccessful && request.body() != null) {
                Either.Success(data = request.body())
            } else {
                if (request.code() == 400) {
                    Either.Failure(error = UnknownHostError)
                } else if (request.code() == 401) {
                    Either.Failure(error = Unauthorized)
                } else {
                    Either.Failure(
                        ApiError(
                            code = request.code(),
                            message = request.errorBody().toString()
                        )
                    )
                }
            }
        } catch (e: IOException) {
            Either.Failure(
                ApiError(
                    code = request.code(),
                    message = request.errorBody().toString()
                )
            )
        } catch (e: Exception) {
            Either.Failure(
                ApiError(
                    code = request.code(),
                    message = request.errorBody().toString()
                )
            )
        }
    }
}