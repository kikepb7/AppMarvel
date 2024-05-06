package com.enriquepalmadev.data_layer.feature.series.repository

import com.enriquepalmadev.data_layer.feature.series.api.datasource.SerieRemoteDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.utils.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.utils.ResponseEitherDomain
import org.mapstruct.factory.Mappers
import javax.inject.Inject

class FilmSerieRepositoryImpl @Inject constructor(
    private val serieRemoteDataSource: SerieRemoteDataSourceImpl
): IFilmSerieRepository {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)
    // private val serieRemoteDataSourceImpl: ISerieRemoteDataSource = SerieRemoteRemoteDataSourceImpl()

    override suspend fun getListOfAllSeries(): ResponseEitherDomain<FailureDomain, List<FilmSerieModel>?> {
        return when(val responseEither = serieRemoteDataSource.getListOfAllSeries()){
            is ResponseEitherDomain.Failure -> ResponseEitherDomain.Failure(responseEither.error)
            is ResponseEitherDomain.Success -> ResponseEitherDomain.Success(responseEither.data?.data?.results?.let { handlerSuccessGetAllSeries(it) })
        }
    }

    private fun handlerSuccessGetAllSeries(list: List<MarvelFilmSerieItemDto>): List<FilmSerieModel> {
        return list.map { marvelFilmSerieItemDto ->
            mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelFilmSerieItemDto)
        }
    }

    override suspend fun getSerieById(id: Int): ResponseEitherDomain<FailureDomain, FilmSerieModel> {
        return when(val responseEither = serieRemoteDataSource.getSerieById(id)){
            is ResponseEitherDomain.Failure -> ResponseEitherDomain.Failure(responseEither.error)
            is ResponseEitherDomain.Success -> ResponseEitherDomain.Success(handlerSuccessGetSerie(responseEither.data))
        }
    }

    private fun handlerSuccessGetSerie(serie: MarvelFilmSerieItemDto): FilmSerieModel {
        return mapper.marvelFilmSerieItemDtoToFilmSerieModel(serie)
    }

    override suspend fun orderListByStartYear(series: List<FilmSerieModel>): ResponseEitherDomain<FailureDomain, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEitherDomain.Success(series.sortedByDescending { it.startYear })
            } else {
                ResponseEitherDomain.Failure(error = FailureDomain.EmptyError)
            }

        } catch (e: Exception) {
            ResponseEitherDomain.Failure(error = FailureDomain.CustomError(
                e.toString(),
                e.message.toString()
            )
            )
        }
    }

    override suspend fun orderListByAlphabet(series: List<FilmSerieModel>): ResponseEitherDomain<FailureDomain, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEitherDomain.Success(data = series.sortedBy { it.title })
            } else {
                ResponseEitherDomain.Failure(error = FailureDomain.EmptyError)
            }

        } catch (e: Exception) {
            ResponseEitherDomain.Failure(error = FailureDomain.CustomError(
                e.toString(),
                e.message.toString()
            )
            )
        }
    }

    override suspend fun filterByName(
        newText: String,
        series: List<FilmSerieModel>
    ): ResponseEitherDomain<FailureDomain, List<FilmSerieModel>> {
        return try {
            if (series.isNotEmpty()) {
                ResponseEitherDomain.Success(data = series.filter {
                    it.title.lowercase().contains(newText.lowercase())
                })
            } else {
                ResponseEitherDomain.Failure(error = FailureDomain.EmptyError)
            }

        } catch (e: Exception) {
            ResponseEitherDomain.Failure(error = FailureDomain.CustomError(
                e.toString(),
                e.message.toString()
            )
            )
        }
    }
}