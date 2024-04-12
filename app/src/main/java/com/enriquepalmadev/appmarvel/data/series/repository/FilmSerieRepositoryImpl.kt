package com.enriquepalmadev.appmarvel.data.series.repository

import android.util.Log
import com.enriquepalmadev.appmarvel.data.series.api.RetrofitBuilder
import com.enriquepalmadev.appmarvel.domain.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.series.repository.IFilmSerieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers

class FilmSerieRepositoryImpl: IFilmSerieRepository {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)

    override suspend fun getListOfAllSeries(): ArrayList<FilmSerieModel> {
        val series: ArrayList<FilmSerieModel> = ArrayList()

        withContext(Dispatchers.IO) {
            val response = RetrofitBuilder.retrofitService.getListOfAllSeries()
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
        return ArrayList(series.sortedByDescending { it.startYear })
    }

    override suspend fun getSerieById(id: Int): FilmSerieModel {
        lateinit var serie: FilmSerieModel

        withContext(Dispatchers.IO){
            val response = RetrofitBuilder.retrofitService.getSerieById(id)
            val result = response.body()?.data?.results

            if(response.isSuccessful){
                if(result != null){
                    serie = mapper.marvelFilmSerieItemDtoToFilmSerieModel(result.first())
                }
            }
        }
        return serie
    }

    override suspend fun orderListByStartYear(series: ArrayList<FilmSerieModel>): ArrayList<FilmSerieModel> {
        return ArrayList(series.sortedByDescending { it.startYear })
    }

    override suspend fun orderListByAlphabet(series: ArrayList<FilmSerieModel>): ArrayList<FilmSerieModel> {
        return ArrayList(series.sortedBy { it.title })
    }

    override suspend fun filterByName(newText: String, series: ArrayList<FilmSerieModel>): ArrayList<FilmSerieModel> {
        return ArrayList(series.filter { it.title.lowercase().contains(newText.lowercase()) })
    }
}