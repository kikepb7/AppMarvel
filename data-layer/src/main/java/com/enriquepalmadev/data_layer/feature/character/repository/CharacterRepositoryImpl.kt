package com.enriquepalmadev.data_layer.feature.character.repository

import com.enriquepalmadev.data_layer.feature.character.database.dao.CharacterDAO
import com.enriquepalmadev.data_layer.feature.character.database.entity.CharacterEntity
import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterEntity
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterErrorDomain
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterListModel
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterModel
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val characterDao: CharacterDAO
) : CharacterRepository {

    //From API
    override suspend fun getCharacterList(): Either<CharacterErrorModel, List<CharacterModel>?> {
        val localData = getCharacterListFromDatabase()
        if (!localData.isNullOrEmpty()) {
            return Either.Success(localData)
        }
        return when (val characterResponse = remoteDataSource.getCharactersFromApi()) {
            is Either.Success -> {
                val characterList = characterResponse.data.data?.results?.toCharacterListModel()
                if (characterList != null) {
                    insertCharacters(characterList.map { it.toCharacterEntity() })
                }
                Either.Success(characterList)
            }
            is Either.Error -> {
                Either.Error(characterResponse.error.toCharacterErrorDomain())
            }
        }
    }

    override suspend fun getCharacterDetail(characterId: Int): Either<CharacterErrorModel, CharacterModel?> {
        val localData = getCharacterListFromDatabase()?.find {
            it.id == characterId
        }

        if(localData != null){
            return Either.Success(localData)
        }

        return when (val characterResponse =
            remoteDataSource.getCharacterDetailFromApi(characterId)) {
            is Either.Success -> {
                val characterDetail =
                    characterResponse.data.data?.results?.firstOrNull()?.toCharacterModel()
                Either.Success(characterDetail)
            }

            is Either.Error -> {
                Either.Error(characterResponse.error.toCharacterErrorDomain())
            }
        }
    }

    //From database
    override suspend fun getCharacterListFromDatabase(): List<CharacterModel>?{
        val response = characterDao.getAllCharacters()
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharacterFilterListFromDatabase(filt: String): List<CharacterModel>? {
        val response = characterDao.getCharactersFilterlist(filt)
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharactersOrderByNameAZ(): List<CharacterModel>?{
        val response = characterDao.getCharactersOrderbyNameAZ()
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharactersOrderByNameZA(): List<CharacterModel>?{
        val response = characterDao.getCharactersOrderbyNameZA()
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharactersOrderByFavourites(): List<CharacterModel>? {
        val response = characterDao.getCharactersOrderByFavourites()
        return response?.map {
            it.toCharacterModel()
        }
    }

    override suspend fun getCharacterDetailFromDatabase(characterId: Int): CharacterModel? {
        val response = characterDao.getCharacterDetail(characterId)
        return response?.toCharacterModel()
    }

    override suspend fun modifierFavouriteCharacter(characterId: Int, isFavourite: Boolean) {
        // Obtener el personaje de la base de datos local
        val character = characterDao.getCharacterDetail(characterId)
        character?.let {
            it.favourite = isFavourite
            // Actualizar el personaje en la base de datos local
            characterDao.updateFavouriteCharacter(it)
        }
    }

    suspend fun insertCharacters(characters: List<CharacterEntity>) {
        characterDao.insertAll(characters)
    }

    override suspend fun clearCharacters() {
        characterDao.deleteAllCharactersFromLocal()
    }
}