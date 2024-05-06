package com.enriquepalmadev.data_layer.feature.series.api.datasource

import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.data_layer.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.data_layer.feature.series.api.utils.CustomError
import com.enriquepalmadev.data_layer.feature.series.api.utils.UnauthorizedError
import com.enriquepalmadev.data_layer.feature.series.api.utils.UnknownHostError
import com.enriquepalmadev.data_layer.feature.series.api.utils.toFailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEitherDomain
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

    override suspend fun getListOfAllSeries(): ResponseEitherDomain<FailureDomain, ObjectResponseDto?> {
        return try {
            val response = api.getListOfAllSeries()
            if (response.isSuccessful) {
                ResponseEitherDomain.Success(response.body())
            } else {
                if (response.code() == Constants.ERROR_401) {
                    ResponseEitherDomain.Failure(UnauthorizedError.toFailureDomain())
                } else {
                    ResponseEitherDomain.Failure(
                        CustomError(
                            response.code().toString(),
                            response.errorBody().toString()
                        ).toFailureDomain()
                    )
                }
            }
        } catch (e: UnknownHostException) {
            ResponseEitherDomain.Failure(UnknownHostError.toFailureDomain())
        } catch (e: Exception) {
            ResponseEitherDomain.Failure(
                CustomError(
                    e.toString(),
                    e.message.toString()
                ).toFailureDomain()
            )
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEitherDomain<FailureDomain, MarvelFilmSerieItemDto> {
        return try {
            val response = api.getSerieById(id)
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    ResponseEitherDomain.Success(result.first())
                } else {
                    ResponseEitherDomain.Failure(
                        CustomError(
                            response.code().toString(),
                            response.errorBody().toString()
                        ).toFailureDomain()
                    )
                }
            } else {
                ResponseEitherDomain.Failure(
                    CustomError(
                        response.code().toString(),
                        response.errorBody().toString()
                    ).toFailureDomain()
                )
            }
        } catch (e: UnknownHostException) {
            ResponseEitherDomain.Failure(UnknownHostError.toFailureDomain())
        } catch (e: Exception) {
            ResponseEitherDomain.Failure(
                CustomError(
                    e.toString(),
                    e.message.toString()
                ).toFailureDomain()
            )
        }
    }
}