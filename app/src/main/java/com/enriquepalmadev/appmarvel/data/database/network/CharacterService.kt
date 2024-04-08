package com.enriquepalmadev.appmarvel.data.database.network

import com.enriquepalmadev.appmarvel.data.database.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
/*
class CharacterService@Inject constructor(private val api: CharacterApiClient) {

    suspend fun getCharacters(): List<CharacterModel>{
        return withContext(Dispatchers.IO){
            val response = api.getAllCharacters()
            response.body() ?: emptyList()
        }
    }


    suspend fun getCharacter(): CharacterModel{
        return withContext(Dispatchers.IO){
            val response = api.getCharacterById()
            response.body() ?: emptyList()
        }
    }
}

 */