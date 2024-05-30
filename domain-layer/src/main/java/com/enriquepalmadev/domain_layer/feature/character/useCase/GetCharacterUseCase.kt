package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCharacterUseCase @Inject constructor(
    private val characterListRepository : CharacterRepository
){

    suspend fun getCharacterList(): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {
        return flow {
            val response = characterListRepository.getCharacterList()
            emit(response)
        }
    }
}

