package com.enriquepalmadev.appmarvel.data.datasource

import com.enriquepalmadev.appmarvel.data.CharacterDataSource
import com.enriquepalmadev.appmarvel.data.Retrofit
import com.enriquepalmadev.appmarvel.data.model.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.model.ResultDTO
import com.enriquepalmadev.appmarvel.data.utilsData.Constants

class CharacterRemoteDataSource: CharacterDataSource {

    private val retrofit = Retrofit.retrofitConection()

    override suspend fun fetchCharactersFromApi(): CharacterResponseDTO<ResultDTO>{
        return retrofit.getAllCharacters(hash = Constants.HASH, ts = Constants.TS, limit = 100)
    }

    override suspend fun fetchCharacterDetailFromApi(characterId: Int): CharacterResponseDTO<ResultDTO>{
        return retrofit.getCharacterById(hash = Constants.HASH, ts = Constants.TS, characterId = characterId)
    }
}