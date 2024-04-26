package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Constants
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import retrofit2.Response

class CharacterRemoteDataSource: CharacterDataSource {

    private val retrofit = Retrofit.retrofitService

    override suspend fun getCharactersFromApi(): Either<String, CharacterResponseDTO<ResultDTO>> {

        val response: Response<CharacterResponseDTO<ResultDTO>> = retrofit.getAllCharacters(hash = Constants.HASH, ts = Constants.TS, limit = 100)
        return if(response.isSuccessful){
            val body = response.body()
            if(body != null){
                Either.Success(body)
            }else{
                Either.Error("Lista vacia.")//Error empty.
            }
        }else{
            Either.Error("Error al obtener los personajes: ${response.code()}")
        }//TODO Tipos de Errores
    }

    override suspend fun getCharacterDetailFromApi(characterId: Int): Either<String, CharacterResponseDTO<ResultDTO>>{
        val response: Response<CharacterResponseDTO<ResultDTO>> = retrofit.getCharacterById(characterId = characterId, hash = Constants.HASH, ts = Constants.TS)
        return if(response.isSuccessful){
            val body = response.body()
            if(body != null){
                Either.Success(body)
            }else{
                Either.Error("Lista vacia.")
            }
        }else{
            Either.Error("Error al obtener el personaje: ${response.code()}")
        }
    }
}