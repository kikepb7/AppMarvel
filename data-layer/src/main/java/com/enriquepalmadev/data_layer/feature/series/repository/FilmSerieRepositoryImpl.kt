package com.enriquepalmadev.data_layer.feature.series.repository

import com.enriquepalmadev.data_layer.feature.series.api.datasource.SerieRemoteDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEither
import org.mapstruct.factory.Mappers
import javax.inject.Inject

class FilmSerieRepositoryImpl @Inject constructor(
    private val serieRemoteDataSource: SerieRemoteDataSourceImpl
): IFilmSerieRepository {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): ResponseEither<FailureDomain, List<FilmSerieModel>?> {
        return when(val responseEither = serieRemoteDataSource.getListOfAllSeries()){
            is ResponseEither.Failure -> ResponseEither.Failure(failure = responseEither.failure)
            is ResponseEither.Success -> ResponseEither.Success(success = responseEither.success?.data?.results?.let { handlerSuccessGetAllSeries(it) })
        }
    }

    private fun handlerSuccessGetAllSeries(list: List<MarvelFilmSerieItemDto>): List<FilmSerieModel> {
        return list.map { marvelFilmSerieItemDto ->
            mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelFilmSerieItemDto)
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEither<FailureDomain, FilmSerieModel> {
        return when(val responseEither = serieRemoteDataSource.getSerieById(id)){
            is ResponseEither.Failure -> ResponseEither.Failure(failure = responseEither.failure)
            is ResponseEither.Success -> ResponseEither.Success(success = handlerSuccessGetSerie(responseEither.success))
        }
    }

    private fun handlerSuccessGetSerie(serie: MarvelFilmSerieItemDto): FilmSerieModel {
        return mapper.marvelFilmSerieItemDtoToFilmSerieModel(serie)
    }

    override suspend fun orderListByStartYear(series: List<FilmSerieModel>): ResponseEither<FailureDomain, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEither.Success(success = series.sortedByDescending { it.startYear })
            } else {
                ResponseEither.Failure(failure = FailureDomain.EmptyErrorDomain)
            }

        } catch (e: Exception) {
            ResponseEither.Failure(failure = FailureDomain.CustomErrorDomain(
                e.toString(),
                e.message.toString()
            )
            )
        }
    }

    override suspend fun orderListByAlphabet(series: List<FilmSerieModel>): ResponseEither<FailureDomain, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEither.Success(success = series.sortedBy { it.title })
            } else {
                ResponseEither.Failure(failure = FailureDomain.EmptyErrorDomain)
            }

        } catch (e: Exception) {
            ResponseEither.Failure(failure = FailureDomain.CustomErrorDomain(
                e.toString(),
                e.message.toString()
            )
            )
        }
    }

    override suspend fun filterByName(
        newText: String,
        series: List<FilmSerieModel>
    ): ResponseEither<FailureDomain, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEither.Success(success = series.filter {
                    it.title.lowercase().contains(newText.lowercase())
                })
            } else {
                ResponseEither.Failure(failure = FailureDomain.EmptyErrorDomain)
            }

        } catch (e: Exception) {
            ResponseEither.Failure(failure = FailureDomain.CustomErrorDomain(
                e.toString(),
                e.message.toString()
            )
            )
        }
    }
}