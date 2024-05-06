package com.enriquepalmadev.data_layer.feature.comics.datasource

import com.enriquepalmadev.data_layer.feature.comics.dto.ApiError
import com.enriquepalmadev.data_layer.feature.comics.dto.Failure
import com.enriquepalmadev.data_layer.feature.comics.dto.ResponseMarvelDto
import com.enriquepalmadev.data_layer.feature.comics.dto.Unauthorized
import com.enriquepalmadev.data_layer.feature.comics.dto.UnknownHostError
import com.enriquepalmadev.data_layer.feature.comics.service.ComicService
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import java.io.IOException
import javax.inject.Inject

class ComicRemoteDataSource @Inject constructor(
    private val retrofitService: ComicService
) {

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