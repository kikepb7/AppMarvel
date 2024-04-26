package com.enriquepalmadev.appmarvel.data.feature.character.repository

import com.enriquepalmadev.appmarvel.data.feature.character.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import com.enriquepalmadev.appmarvel.data.feature.character.utils.extensions.toCharacterListModel
import com.enriquepalmadev.appmarvel.data.feature.character.utils.extensions.toCharacterModel
import com.enriquepalmadev.appmarvel.domain.feature.character.CharacterRepository
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel


object CharacterRepositoryImpl: CharacterRepository {

    private val remoteDataSource = CharacterRemoteDataSource()
    private var cachedCharacterList: List<CharacterModel>? = null

    override suspend fun getCharacterList(): Either<String, List<CharacterModel>?> {
        if(cachedCharacterList != null){//Si ya se ha guardado utilizamos la lista guardada en cache.
            return Either.Success(cachedCharacterList)
        }
        return try {
            when(val characterResponse = remoteDataSource.getCharactersFromApi()){
                is Either.Success -> {
                    val characterList = characterResponse.data.data?.results?.toCharacterListModel()
                    cachedCharacterList = characterList
                    Either.Success(characterList)
                }
                is Either.Error -> {
                    Either.Error("Error: La solicitud a la API no fue exitosa")
                }
            }
        }catch (e: Exception){
            Either.Error("Error: La solicitud a la API no fue exitosa")
        }
    }

    override suspend fun getCharacterDetail(characterId: Int): Either<String, CharacterModel?> {
        return try {
            when(val characterResponse = remoteDataSource.getCharacterDetailFromApi(characterId)){
                is Either.Success -> {
                    val characterDetail = characterResponse.data.data?.results?.firstOrNull()?.toCharacterModel()
                    Either.Success(characterDetail)
                }
                is Either.Error -> {
                    Either.Error("Error: La solicitud a la API no fue exitosa")
                }
            }
        }catch (e: Exception){
            Either.Error("Error: La solicitud a la API no fue exitosa")
        }
    }
}