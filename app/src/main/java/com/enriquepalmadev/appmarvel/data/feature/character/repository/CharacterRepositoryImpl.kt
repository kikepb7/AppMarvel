package com.enriquepalmadev.appmarvel.data.feature.character.repository

import com.enriquepalmadev.appmarvel.data.feature.character.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.appmarvel.data.feature.character.utilsData.Either
import com.enriquepalmadev.appmarvel.data.feature.character.utilsData.toCharacterListModel
import com.enriquepalmadev.appmarvel.data.feature.character.utilsData.toCharacterModel
import com.enriquepalmadev.appmarvel.domain.feature.character.CharacterRepository
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel


object CharacterRepositoryImpl: CharacterRepository {

    private val remoteDataSource = CharacterRemoteDataSource()
    private var cachedCharacterList: List<CharacterModel>? = null

    override suspend fun getCharacterList(): Either<String, List<CharacterModel>?> {
        if(cachedCharacterList != null){//Si ya se ha guardado utilizamos la lista guardada en cache.
            return Either.Success(cachedCharacterList)
        }
        val response = remoteDataSource.getCharactersFromApi()
        if(response.isSuccessful){
            val characterList = remoteDataSource.getCharactersFromApi().body()?.data?.results?.toCharacterListModel()
            cachedCharacterList = characterList//Guardamos la lista en la primera llamada.
            return Either.Success(characterList)//Renombrar por success or failure
        }else{
            return Either.Error("Error: La solicitud no fue exitosa")
        }//Dto to ListModel
    }

    //Verificar
    override suspend fun getCharacterDetail(characterId: Int): Either<String, CharacterModel?> {
        val response = remoteDataSource.getCharacterDetailFromApi(characterId)
        if(response.isSuccessful){
            val character = remoteDataSource.getCharacterDetailFromApi(characterId).body()?.data?.results?.firstOrNull()?.toCharacterModel()
            return Either.Success(character)
        }else{
            return Either.Error("Error: La solicitud no fue exitosa")
        }
    }
}