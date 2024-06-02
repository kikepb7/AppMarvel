package com.enriquepalmadev.data_layer.feature.series.api.datasource

import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.data_layer.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.data_layer.feature.series.failure.CustomErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnauthorizedErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnknownHostErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.FailureData
import com.enriquepalmadev.domain_layer.commons.Either
import java.net.UnknownHostException
import javax.inject.Inject

class SerieRemoteDataSourceImpl @Inject constructor(
    private val api: IMarvelFilmSerieService
) : ISerieRemoteDataSource {

    override suspend fun getListOfAllSeries(): Either<FailureData, ObjectResponseDto?> {
        return try {
            val response = api.getListOfAllSeries()
            if (response.isSuccessful) {
                Either.Success(data = response.body())
            } else {
                if (response.code() == Constants.ERROR_401) {
                    Either.Error(error = UnauthorizedErrorData)
                } else {
                    Either.Error(error = CustomErrorData(
                            response.code().toString(),
                            response.errorBody().toString()
                        )
                    )
                }
            }
        } catch (e: UnknownHostException) {
            Either.Error(error = UnknownHostErrorData)
        } catch (e: Exception) {
            Either.Error(error = CustomErrorData(
                    e.toString(),
                    e.message.toString()
                )
            )
        }
    }

    override suspend fun getSerieById(id: Int): Either<FailureData, MarvelFilmSerieItemDto> {
        return try {
            val response = api.getSerieById(id)
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    Either.Success(data = result.first())
                } else {
                    Either.Error(error = CustomErrorData(
                            response.code().toString(),
                            response.errorBody().toString()
                        )
                    )
                }
            } else {
                Either.Error(error = CustomErrorData(
                        response.code().toString(),
                        response.errorBody().toString()
                    )
                )
            }
        } catch (e: UnknownHostException) {
            Either.Error(error = UnknownHostErrorData)
        } catch (e: Exception) {
            Either.Error(error = CustomErrorData(
                    e.toString(),
                    e.message.toString()
                )
            )
        }
    }
}