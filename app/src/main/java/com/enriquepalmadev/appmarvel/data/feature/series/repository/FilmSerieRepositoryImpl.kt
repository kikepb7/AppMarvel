package com.enriquepalmadev.appmarvel.data.feature.series.repository

import com.enriquepalmadev.appmarvel.data.feature.series.api.RetrofitBuilder
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.CustomError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Constants
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.EmptyError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnauthorizedError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnknownHostError
import com.enriquepalmadev.appmarvel.domain.feature.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.feature.series.repository.IFilmSerieRepository
import org.mapstruct.factory.Mappers
import java.net.UnknownHostException

class FilmSerieRepositoryImpl : IFilmSerieRepository {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): ResponseEither<Failure, List<FilmSerieModel>?> {
        return try {
            val response = RetrofitBuilder.retrofitService.getListOfAllSeries()
            if (response.isSuccessful) {
                ResponseEither.Success(r = response.body()?.data?.results
                    ?.map { marvelFilmSerieItemDto ->
                        mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelFilmSerieItemDto)
                    })
            } else {

                if (response.code() == Constants.ERROR_401) {
                    ResponseEither.Failure(l = UnauthorizedError)
                } else {
                    ResponseEither.Failure(
                        l = CustomError(
                            response.code(),
                            response.errorBody().toString()
                        )
                    )
                }
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(l = UnknownHostError)
        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(0, e.message.toString()))
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEither<Failure, FilmSerieModel> {
        return try {
            val response = RetrofitBuilder.retrofitService.getSerieById(id)
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    ResponseEither.Success(r = mapper.marvelFilmSerieItemDtoToFilmSerieModel(result.first()))
                } else {
                    ResponseEither.Failure(
                        l = CustomError(
                            response.code(),
                            response.errorBody().toString()
                        )
                    )
                }
            } else {
                ResponseEither.Failure(
                    l = CustomError(
                        response.code(),
                        response.errorBody().toString()
                    )
                )
            }
        } catch (e: UnknownHostException) {
            ResponseEither.Failure(l = UnknownHostError)
        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(0, e.message.toString()))
        }
    }

    override suspend fun orderListByStartYear(series: List<FilmSerieModel>): ResponseEither<Failure, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEither.Success(r = series.sortedByDescending { it.startYear })
            } else {
                ResponseEither.Failure(l = EmptyError)
            }

        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(0, e.message.toString()))
        }
    }

    override suspend fun orderListByAlphabet(series: List<FilmSerieModel>): ResponseEither<Failure, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEither.Success(r = series.sortedBy { it.title })
            } else {
                ResponseEither.Failure(l = EmptyError)
            }

        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(0, e.message.toString()))
        }
    }

    override suspend fun filterByName(
        newText: String,
        series: List<FilmSerieModel>
    ): ResponseEither<Failure, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEither.Success(r = series.filter {
                    it.title.lowercase().contains(newText.lowercase())
                })
            } else {
                ResponseEither.Failure(l = EmptyError)
            }

        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(0, e.message.toString()))
        }
    }
}