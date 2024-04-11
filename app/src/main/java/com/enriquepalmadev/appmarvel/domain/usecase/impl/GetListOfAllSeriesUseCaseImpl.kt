package com.enriquepalmadev.appmarvel.domain.usecase.impl

import android.util.Log
import com.enriquepalmadev.appmarvel.data.api.RetrofitBuilder.retrofitService
import com.enriquepalmadev.appmarvel.domain.mapper.IFilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.usecase.IGetListOfAllSeriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers

class GetListOfAllSeriesUseCaseImpl : IGetListOfAllSeriesUseCase {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): Flow<ArrayList<FilmSerieModel>> {
        val series: ArrayList<FilmSerieModel> = ArrayList()

        withContext(Dispatchers.IO) {
            val response = retrofitService.getListOfAllSeries()
            val result = response.body()?.data?.results

            if (response.isSuccessful) {
                if (result != null) {
                    for (serie in result) {
                        if (!serie.description.isNullOrEmpty()) {
                            series.add(mapper.marvelFilmSerieItemDtoToFilmSerieModel(serie))
                        }
                    }
                } else {
                    Log.d("APIError:::", "error en el UseCase GetListOfAllSeries IMPL")
                }
            }
        }
        return flow { emit(series) }
    }
}