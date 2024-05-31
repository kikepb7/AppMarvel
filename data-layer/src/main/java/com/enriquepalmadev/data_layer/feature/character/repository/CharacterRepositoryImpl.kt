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
    private val characterLocalRepositoryImpl: CharacterLocalRepositoryImpl
) : CharacterRepository {

    //From API
    override suspend fun getCharacterList(): Either<CharacterErrorModel, List<CharacterModel>?> {
        val localData = characterLocalRepositoryImpl.getAllCharacters()
        if (!localData.isNullOrEmpty()) {
            return Either.Success(localData)
        }
        return when (val characterResponse = remoteDataSource.getCharactersFromApi()) {
            is Either.Success -> {
                val characterList = characterResponse.data.data?.results?.toCharacterListModel()
                if (characterList != null) {
                    characterLocalRepositoryImpl.insertAll(characterList.map { it.toCharacterEntity() })
                }
                Either.Success(characterList)
            }
            is Either.Error -> {
                Either.Error(characterResponse.error.toCharacterErrorDomain())
            }
        }
    }

    override suspend fun getCharacterDetail(characterId: Int): Either<CharacterErrorModel, CharacterModel?> {
        val localData = characterLocalRepositoryImpl.getAllCharacters()?.find {
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
}