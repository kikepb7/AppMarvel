package com.enriquepalmadev.appmarvel.domain.usecase.impl

import com.enriquepalmadev.appmarvel.data.api.RetrofitBuilder.retrofitService
import com.enriquepalmadev.appmarvel.domain.mapper.IFilmSerieMapper
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.usecase.IGetSerieByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers

class GetSerieByIdUseCaseImpl: IGetSerieByIdUseCase {

    private val mapper: IFilmSerieMapper = Mappers.getMapper(IFilmSerieMapper::class.java)

    override suspend fun getSerieById(id: Int): Flow<FilmSerieModel> {
        lateinit var serie: FilmSerieModel

        // Repositorio
        withContext(Dispatchers.IO){
            val response = retrofitService.getSerieById(id)
            val result = response.body()?.data?.results

            if(response.isSuccessful){
                if(result != null){
                    serie = mapper.marvelFilmSerieItemDtoToFilmSerieModel(result.first())
                }
            }
        }
        return flow { emit(serie) }
    }
}