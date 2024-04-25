package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Constants
import retrofit2.Response

class CharacterRemoteDataSource: CharacterDataSource {

    private val retrofit = Retrofit.retrofitService

    override suspend fun getCharactersFromApi(): CharacterResponseDTO<ResultDTO> {

        val response: Response<CharacterResponseDTO<ResultDTO>> = retrofit.getAllCharacters(hash = Constants.HASH, ts = Constants.TS, limit = 100)
        if(response.isSuccessful){
            return response.body() ?: throw Exception("Lista vacia")//Either error
        }else{
            throw Exception("Error al coger los personajes: ${response.code()}")
        }
    }

    override suspend fun getCharacterDetailFromApi(characterId: Int): CharacterResponseDTO<ResultDTO>{
        val response: Response<CharacterResponseDTO<ResultDTO>> = retrofit.getCharacterById(characterId = characterId, hash = Constants.HASH, ts = Constants.TS)
        if(response.isSuccessful){
            return response.body() ?: throw Exception("Lista vacia")
        }else{
            throw Exception("Error al coger el personaje: ${response.code()}")
        }
    }
}