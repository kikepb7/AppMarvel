package com.enriquepalmadev.data_layer.feature.series.api.datasource

import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.data_layer.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.data_layer.feature.series.failure.CustomErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnauthorizedErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnknownHostErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.FailureData
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import java.net.UnknownHostException
import javax.inject.Inject

class SerieRemoteDataSourceImpl @Inject constructor(
    private val api: IMarvelFilmSerieService
) : ISerieRemoteDataSource {

    override suspend fun getListOfAllSeries(): ResponseEither<FailureData, ObjectResponseDto?> {
        return try {
            val response = api.getListOfAllSeries()
            if (response.isSuccessful) {
                ResponseEither.Success(success = response.body())
            } else {
                if (response.code() == Constants.ERROR_401) {
                    ResponseEither.Failure(failure = UnauthorizedErrorData)
                } else {
                    ResponseEither.Failure(failure = CustomErrorData(
                            response.code().toString(),
                            response.errorBody().toString()
                        )
                    )
                }
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(failure = UnknownHostErrorData)
        } catch (e: Exception) {
            ResponseEither.Failure(failure = CustomErrorData(
                    e.toString(),
                    e.message.toString()
                )
            )
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEither<FailureData, MarvelFilmSerieItemDto> {
        return try {
            val response = api.getSerieById(id)
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    ResponseEither.Success(success = result.first())
                } else {
                    ResponseEither.Failure(failure = CustomErrorData(
                            response.code().toString(),
                            response.errorBody().toString()
                        )
                    )
                }
            } else {
                ResponseEither.Failure(failure = CustomErrorData(
                        response.code().toString(),
                        response.errorBody().toString()
                    )
                )
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(failure = UnknownHostErrorData)
        } catch (e: Exception) {
            ResponseEither.Failure(failure = CustomErrorData(
                    e.toString(),
                    e.message.toString()
                )
            )
        }
    }
}