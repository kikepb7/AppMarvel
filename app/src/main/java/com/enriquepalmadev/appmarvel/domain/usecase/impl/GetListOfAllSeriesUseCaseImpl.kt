package com.enriquepalmadev.appmarvel.domain.usecase.impl

import com.enriquepalmadev.appmarvel.data.api.API.retrofitService
import com.enriquepalmadev.appmarvel.domain.mapper.FilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import com.enriquepalmadev.appmarvel.domain.usecase.IGetListOfAllSeriesUseCase
import org.mapstruct.factory.Mappers

class GetListOfAllSeriesUseCaseImpl : IGetListOfAllSeriesUseCase{

    val mapper = Mappers.getMapper(FilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): ArrayList<FilmSerie> {

            val response = retrofitService.getListOfAllSeries("/v1/public/series")
            val series: ArrayList<FilmSerie> = ArrayList()
            if(response.isSuccessful && !response.body().isNullOrEmpty()){
                for (serie in response.body()!!) {
                    series.add(mapper.marvelFilmSerieItemDtoToFilmSerie(serie))
                }
            }
        return series
    }

}