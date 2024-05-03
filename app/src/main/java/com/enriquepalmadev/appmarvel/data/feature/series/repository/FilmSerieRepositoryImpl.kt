package com.enriquepalmadev.appmarvel.data.feature.series.repository

import com.enriquepalmadev.appmarvel.data.feature.series.api.datasource.SerieRemoteRemoteDataSourceImpl
import com.enriquepalmadev.appmarvel.data.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.CustomError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.EmptyError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Failure
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.ResponseEither
import com.enriquepalmadev.appmarvel.domain.feature.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.feature.series.repository.IFilmSerieRepository
import org.mapstruct.factory.Mappers
import javax.inject.Inject

class FilmSerieRepositoryImpl @Inject constructor(
    private val serieRemoteDataSource: SerieRemoteRemoteDataSourceImpl
): IFilmSerieRepository {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)
    // private val serieRemoteDataSourceImpl: ISerieRemoteDataSource = SerieRemoteRemoteDataSourceImpl()

    override suspend fun getListOfAllSeries(): ResponseEither<Failure, List<FilmSerieModel>?> {
        return when(val responseEither = serieRemoteDataSource.getListOfAllSeries()){
            is ResponseEither.Failure -> ResponseEither.Failure(l = responseEither.l)
            is ResponseEither.Success -> ResponseEither.Success(r = responseEither.r?.data?.results?.let { handlerSuccessGetAllSeries(it) })
        }
    }

    private fun handlerSuccessGetAllSeries(list: List<MarvelFilmSerieItemDto>): List<FilmSerieModel> {
        return list.map { marvelFilmSerieItemDto ->
            mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelFilmSerieItemDto)
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEither<Failure, FilmSerieModel> {
        return when(val responseEither = serieRemoteDataSource.getSerieById(id)){
            is ResponseEither.Failure -> ResponseEither.Failure(l = responseEither.l)
            is ResponseEither.Success -> ResponseEither.Success(r = handlerSuccessGetSerie(responseEither.r))
        }
    }

    private fun handlerSuccessGetSerie(serie: MarvelFilmSerieItemDto): FilmSerieModel {
        return mapper.marvelFilmSerieItemDtoToFilmSerieModel(serie)
    }

    override suspend fun orderListByStartYear(series: List<FilmSerieModel>): ResponseEither<Failure, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEither.Success(r = series.sortedByDescending { it.startYear })
            } else {
                ResponseEither.Failure(l = EmptyError)
            }

        } catch (e: Exception) {
            ResponseEither.Failure(l = CustomError(e.toString(), e.message.toString()))
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
            ResponseEither.Failure(l = CustomError(e.toString(), e.message.toString()))
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
            ResponseEither.Failure(l = CustomError(e.toString(), e.message.toString()))
        }
    }
}