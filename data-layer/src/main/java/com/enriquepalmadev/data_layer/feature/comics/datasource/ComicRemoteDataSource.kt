package com.enriquepalmadev.data_layer.feature.comics.datasource

import com.enriquepalmadev.data_layer.feature.comics.dto.FailureDto
import com.enriquepalmadev.data_layer.feature.comics.dto.ResponseMarvelDto
import com.enriquepalmadev.data_layer.feature.comics.service.ComicService
import com.enriquepalmadev.domain_layer.commons.Either
import java.io.IOException
import javax.inject.Inject

class ComicRemoteDataSource @Inject constructor(
    private val retrofitService: ComicService
) {

    suspend fun fetchComicsFromApi(): Either<FailureDto, ResponseMarvelDto?> {
        val request = retrofitService.getComics(limit = 100)

        return try {
            if (request.isSuccessful && request.body() != null) {
                Either.Success(data = request.body())
            } else {
                Either.Error(
                    error = FailureDto(
                        code = request.code(),
                        message = request.errorBody().toString()
                    )
                )
            }
        } catch (e: Exception) {
            Either.Error(
                FailureDto(
                    code = request.code(),
                    message = request.errorBody().toString()
                )
            )
        }
    }

    suspend fun fetchComicDetailFromApi(comicId: Int): Either<FailureDto, ResponseMarvelDto?> {
        val request = retrofitService.getComicById(comicId = comicId)

        return try {
            if (request.isSuccessful && request.body() != null) {
                Either.Success(data = request.body())
            } else {
                Either.Error(
                    error = FailureDto(
                        code = request.code(),
                        message = request.errorBody().toString()
                    )
                )
            }
        } catch (e: Exception) {
            Either.Error(
                FailureDto(
                    code = request.code(),
                    message = request.errorBody().toString()
                )
            )
        }
    }
}