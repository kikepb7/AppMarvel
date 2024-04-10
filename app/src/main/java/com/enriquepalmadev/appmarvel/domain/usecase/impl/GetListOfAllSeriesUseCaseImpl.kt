package com.enriquepalmadev.appmarvel.domain.usecase.impl

import android.util.Log
import com.enriquepalmadev.appmarvel.data.api.RetrofitBuilder.retrofitService
import com.enriquepalmadev.appmarvel.domain.mapper.FilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import com.enriquepalmadev.appmarvel.domain.usecase.IGetListOfAllSeriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers

class GetListOfAllSeriesUseCaseImpl : IGetListOfAllSeriesUseCase{

    val mapper = Mappers.getMapper(FilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): ArrayList<FilmSerie> {
        val series: ArrayList<FilmSerie> = ArrayList()
        withContext(Dispatchers.IO){
            val response = retrofitService.getListOfAllSeries()
            response.body()?.data?.results
            if(response.isSuccessful && response.body()!=null){
                for (serie in response.body()!!.data.results) {
                    series.add(mapper.marvelFilmSerieItemDtoToFilmSerie(serie))
                }
            } else {
                Log.d("APIError:::", "error en el UseCase GetListOfAllSeries IMPL")
            }
        }
        return series
    }

}