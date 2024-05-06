package com.enriquepalmadev.data_layer.feature.series.api.datasource

import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.data_layer.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.data_layer.feature.series.failure.CustomErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnauthorizedErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnknownHostErrorData
import com.enriquepalmadev.data_layer.feature.series.api.utils.toFailureDomain
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import java.net.UnknownHostException
import javax.inject.Inject

class SerieRemoteDataSourceImpl @Inject constructor(
    private val api: IMarvelFilmSerieService
) : ISerieDataSource {

    /*
    private val retrofitService: IMarvelFilmSerieService by lazy {
        RetrofitBuilder.getRetrofit().create(IMarvelFilmSerieService::class.java)
    }
     */

    override suspend fun getListOfAllSeries(): ResponseEither<FailureDomain, ObjectResponseDto?> {
        return try {
            val response = api.getListOfAllSeries()
            if (response.isSuccessful) {
                ResponseEither.Success(r = response.body())
            } else {
                if (response.code() == Constants.ERROR_401) {
                    ResponseEither.Failure(l = UnauthorizedErrorData.toFailureDomain())
                } else {
                    ResponseEither.Failure(l = CustomErrorData(
                            response.code().toString(),
                            response.errorBody().toString()
                        ).toFailureDomain()
                    )
                }
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(l = UnknownHostErrorData.toFailureDomain())
        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomErrorData(
                    e.toString(),
                    e.message.toString()
                ).toFailureDomain()
            )
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEither<FailureDomain, MarvelFilmSerieItemDto> {
        return try {
            val response = api.getSerieById(id)
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    ResponseEither.Success(r = result.first())
                } else {
                    ResponseEither.Failure(l = CustomErrorData(
                            response.code().toString(),
                            response.errorBody().toString()
                        ).toFailureDomain()
                    )
                }
            } else {
                ResponseEither.Failure(l = CustomErrorData(
                        response.code().toString(),
                        response.errorBody().toString()
                    ).toFailureDomain()
                )
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(l = UnknownHostErrorData.toFailureDomain())
        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomErrorData(
                    e.toString(),
                    e.message.toString()
                ).toFailureDomain()
            )
        }
    }
}