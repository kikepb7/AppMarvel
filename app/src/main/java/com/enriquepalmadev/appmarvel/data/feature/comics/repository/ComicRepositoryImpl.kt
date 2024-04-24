package com.enriquepalmadev.appmarvel.data.feature.comics.repository

import com.enriquepalmadev.appmarvel.data.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ApiError
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Either
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Failure
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.Unauthorized
import com.enriquepalmadev.appmarvel.data.feature.comics.dto.UnknownHostError
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
                if (request.code() == 400) {
                    Either.Failure(error = UnknownHostError)
                } else if (request.code() == 401){
                    Either.Failure(error = Unauthorized)
                } else {
                    Either.Failure(ApiError(code = request.code(), message = request.errorBody().toString()))
                }
            }
        } catch (e: IOException) {
            Either.Failure(ApiError(code = request.code(), message = request.errorBody().toString()))
        } catch (e: Exception) {
            Either.Failure(ApiError(code = request.code(), message = request.errorBody().toString()))
        }
    }

    override suspend fun fetchComicDetail(comicId: Int): Either<Failure, ComicModel?> {
        val request = remoteDataSource.fetchComicDetailFromApi(comicId)

        return try {
            if (request.isSuccessful && request.body() != null) {
                Either.Success(data = request.body()?.data?.results?.dtoToComicListModel()?.getOrNull(0))
            } else {
                if (request.code() == 400) {
                    Either.Failure(error = UnknownHostError)
                } else if (request.code() == 401){
                    Either.Failure(error = Unauthorized)
                } else {
                    Either.Failure(ApiError(code = request.code(), message = request.errorBody().toString()))
                }
            }
        } catch (e: IOException) {
            Either.Failure(ApiError(code = request.code(), message = request.errorBody().toString()))
        } catch (e: Exception) {
            Either.Failure(ApiError(code = request.code(), message = request.errorBody().toString()))
        }
    }
}