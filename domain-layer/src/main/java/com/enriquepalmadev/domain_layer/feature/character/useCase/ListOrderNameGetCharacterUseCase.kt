package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListOrderNameGetCharacterUseCase @Inject constructor(
    private val characterListRepositoryImpl : CharacterRepository
) {


    suspend fun getCharacterListOrderByNameAZ(): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {

        return flow{
            val localCharacters = characterListRepositoryImpl.getCharactersOrderByNameAZ()
            emit(Either.Success(localCharacters))
        }
    }

    suspend fun getCharacterListOrderByNameZA(): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {

        return flow {
            val localCharacters = characterListRepositoryImpl.getCharactersOrderByNameZA()
            emit(Either.Success(localCharacters))
        }
    }
}