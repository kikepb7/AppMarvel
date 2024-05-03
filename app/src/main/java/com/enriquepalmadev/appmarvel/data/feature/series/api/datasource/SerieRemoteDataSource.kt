package com.enriquepalmadev.appmarvel.data.feature.series.api.datasource

import com.enriquepalmadev.appmarvel.data.feature.series.api.RetrofitBuilder
import com.enriquepalmadev.appmarvel.data.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.appmarvel.data.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.appmarvel.data.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Constants
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.CustomError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnauthorizedError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnknownHostError
import java.net.UnknownHostException

class SerieRemoteDataSource : ISerieDataSource {

    private val retrofitService: IMarvelFilmSerieService by lazy {
        RetrofitBuilder.getRetrofit().create(IMarvelFilmSerieService::class.java)
    }

    override suspend fun getListOfAllSeries(): ResponseEither<Failure, ObjectResponseDto?> {
        return try {
            val response = retrofitService.getListOfAllSeries()
            if (response.isSuccessful) {
                ResponseEither.Success(r = response.body())
            } else {
                if (response.code() == Constants.ERROR_401) {
                    ResponseEither.Failure(l = UnauthorizedError)
                } else {
                    ResponseEither.Failure(
                        l = CustomError(
                            response.code().toString(),
                            response.errorBody().toString()
                        )
                    )
                }
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(l = UnknownHostError)
        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(e.toString(), e.message.toString()))
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEither<Failure, MarvelFilmSerieItemDto> {
        return try {
            val response = retrofitService.getSerieById(id)
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    ResponseEither.Success(r = result.first())
                } else {
                    ResponseEither.Failure(
                        l = CustomError(
                            response.code().toString(),
                            response.errorBody().toString()
                        )
                    )
                }
            } else {
                ResponseEither.Failure(
                    l = CustomError(
                        response.code().toString(),
                        response.errorBody().toString()
                    )
                )
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(l = UnknownHostError)
        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(e.toString(), e.message.toString()))
        }
    }
}