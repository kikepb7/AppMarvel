package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterLocalRepository
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListOrderNameGetCharacterUseCase @Inject constructor(
    private val characterLocalRepository: CharacterLocalRepository
) {


    suspend fun getCharacterListOrderByNameAZ(): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {

        return flow{
            val localCharacters = characterLocalRepository.getCharactersOrderbyNameAZ()
            emit(Either.Success(localCharacters))
        }
    }

    suspend fun getCharacterListOrderByNameZA(): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {

        return flow {
            val localCharacters = characterLocalRepository.getCharactersOrderbyNameZA()
            emit(Either.Success(localCharacters))
        }
    }
}