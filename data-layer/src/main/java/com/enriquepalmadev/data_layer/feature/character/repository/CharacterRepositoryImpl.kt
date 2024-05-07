package com.enriquepalmadev.data_layer.feature.character.repository

import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterErrorDomain
import com.enriquepalmadev.domain_layer.feature.character.utils.Either
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterListModel
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterModel
import com.enriquepalmadev.domain_layer.feature.character.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorDomain
import javax.inject.Inject
import javax.inject.Singleton

//TODO Ya que he implementado las di; ahora ya no se puede hacer singleton sino que es una clase asi que tengo que buscar la manera de cachear la lista y el id.
@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    private var cachedCharacterList: List<CharacterModel>? = null

    override suspend fun getCharacterList(): Either<CharacterErrorDomain, List<CharacterModel>?> {
        if (cachedCharacterList != null) {//Si ya se ha guardado utilizamos la lista guardada en cache.
            return Either.Success(cachedCharacterList)
        }
        return when (val characterResponse = remoteDataSource.getCharactersFromApi()) {
            is Either.Success -> {
                val characterList = characterResponse.data.data?.results?.toCharacterListModel()
                cachedCharacterList = characterList
                Either.Success(characterList)
            }

            is Either.Error -> {
                Either.Error(characterResponse.error.toCharacterErrorDomain())
            }
        }
    }

    override suspend fun getCharacterDetail(characterId: Int): Either<CharacterErrorDomain, CharacterModel?> {
        val cachedCharacter = cachedCharacterList?.find {
            it.id == characterId
        }
        if (cachedCharacter != null) {
            return Either.Success(cachedCharacter)
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