package com.enriquepalmadev.data_layer.feature.series.repository

import com.enriquepalmadev.data_layer.feature.series.api.datasource.SerieRemoteDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.utils.toFailureDomain
import com.enriquepalmadev.data_layer.feature.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import org.mapstruct.factory.Mappers
import javax.inject.Inject

class FilmSerieRepositoryImpl @Inject constructor(
    private val serieRemoteDataSourceImpl: SerieRemoteDataSourceImpl
): IFilmSerieRepository {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): Either<FailureDomain, List<FilmSerieModel>?> {
        return when(val responseEither = serieRemoteDataSourceImpl.getListOfAllSeries()){
            is Either.Error -> Either.Error(error = responseEither.error.toFailureDomain())
            is Either.Success -> Either.Success(data = responseEither.data?.data?.results?.let { handlerSuccessGetAllSeries(it) })
        }
    }

    // Mapping to model
    private fun handlerSuccessGetAllSeries(list: List<MarvelFilmSerieItemDto>): List<FilmSerieModel> {
        return list.map { marvelFilmSerieItemDto ->
            mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelFilmSerieItemDto)
        }
    }

    override suspend fun getSerieById(id: Int): Either<FailureDomain, FilmSerieModel> {
        return when(val responseEither = serieRemoteDataSourceImpl.getSerieById(id)){
            is Either.Error -> Either.Error(error = responseEither.error.toFailureDomain())
            is Either.Success -> Either.Success(data = handlerSuccessGetSerie(responseEither.data))
        }
    }

    // Mapping to model
    private fun handlerSuccessGetSerie(serie: MarvelFilmSerieItemDto): FilmSerieModel {
        return mapper.marvelFilmSerieItemDtoToFilmSerieModel(serie)
    }
}