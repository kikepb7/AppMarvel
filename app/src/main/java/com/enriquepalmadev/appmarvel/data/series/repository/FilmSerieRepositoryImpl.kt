package com.enriquepalmadev.appmarvel.data.series.repository

import android.util.Log
import com.enriquepalmadev.appmarvel.data.series.api.RetrofitBuilder
import com.enriquepalmadev.appmarvel.data.series.api.utils.Constants
import com.enriquepalmadev.appmarvel.data.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.data.series.api.utils.AnotherError
import com.enriquepalmadev.appmarvel.data.series.api.utils.UnauthorizedError
import com.enriquepalmadev.appmarvel.domain.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.series.repository.IFilmSerieRepository
import org.mapstruct.factory.Mappers

class FilmSerieRepositoryImpl : IFilmSerieRepository {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): ResponseEither<Failure, List<FilmSerieModel>?> {

        val response = RetrofitBuilder.retrofitService.getListOfAllSeries()

        return try {
            if (response.isSuccessful) {
                ResponseEither.Success(r = response.body()?.data?.results
                    ?.map { marvelFilmSerieItemDto ->
                        mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelFilmSerieItemDto)
                    })
            } else {

                if(response.code()==Constants.ERROR_401){
                    ResponseEither.Failure(l = UnauthorizedError)
                } else {
                    ResponseEither.Failure(l = AnotherError(response.code(), response.errorBody().toString()))
                }
            }

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEither<Failure, FilmSerieModel> {

        val response = RetrofitBuilder.retrofitService.getSerieById(id)

        return try {
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    ResponseEither.Success(r = mapper.marvelFilmSerieItemDtoToFilmSerieModel(result.first()))
                } else {
                    ResponseEither.Failure(l = AnotherError(response.code(), response.errorBody().toString()))
                }
            } else {
                ResponseEither.Failure(l=AnotherError(response.code(), response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Log.d("Error:::", "in getSerieById() at FilmSerieRepositoryImpl")
            throw e
        }
    }

    override suspend fun orderListByStartYear(series: List<FilmSerieModel>): ResponseEither<Failure, List<FilmSerieModel>> {
         return try {
             if(series.isNotEmpty()){
                 ResponseEither.Success(r = series.sortedByDescending { it.startYear })
             } else {
                 ResponseEither.Failure(l = AnotherError(0, "Empty list"))
             }

        } catch (e: Exception) {
            Log.d("Error:::", "in orderListByStartYear() at FilmSerieRepositoryImpl")
            throw e
        }
    }

    override suspend fun orderListByAlphabet(series: List<FilmSerieModel>): ResponseEither<Failure, List<FilmSerieModel>> {
        return try {
            if(series.isNotEmpty()){
                ResponseEither.Success(r = series.sortedBy { it.title })
            } else {
                ResponseEither.Failure(l = AnotherError(0, "Empty list"))
            }

        } catch (e: Exception) {
            Log.d("Error:::", "in orderListByAlphabet() at FilmSerieRepositoryImpl")
            throw e
        }
    }

    override suspend fun filterByName(
        newText: String,
        series: List<FilmSerieModel>
    ): ResponseEither<Failure, List<FilmSerieModel>> {
        return try {
            if(series.isNotEmpty()){
                ResponseEither.Success(r = series.filter { it.title.lowercase().contains(newText.lowercase()) })
            } else {
                ResponseEither.Failure(l = AnotherError(0, "Empty list"))
            }

        } catch (e: Exception) {
            Log.d("Error:::", "in filterByName() at FilmSerieRepositoryImpl")
            throw e
        }
    }
}