package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.CharacterDataSource
import com.enriquepalmadev.appmarvel.data.feature.character.Retrofit
import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import com.enriquepalmadev.appmarvel.data.feature.character.utilsData.Constants
import retrofit2.Response

class CharacterRemoteDataSource: CharacterDataSource {

    //private val retrofit = Retrofit.retrofitConnection()
    private val retrofit = Retrofit.retrofitService

    override suspend fun getCharactersFromApi(): Response<CharacterResponseDTO<ResultDTO>> {
        val characterResponse = retrofit.getAllCharacters(hash = Constants.HASH, ts = Constants.TS, limit = 100)
        return Response.success(characterResponse)
    }

    override suspend fun getCharacterDetailFromApi(characterId: Int): Response<CharacterResponseDTO<ResultDTO>>{
        val characterIdResponse = retrofit.getCharacterById(characterId = characterId, hash = Constants.HASH, ts = Constants.TS)
        return Response.success(characterIdResponse)
    }
}