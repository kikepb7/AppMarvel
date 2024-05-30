package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val characterListRepository : CharacterRepository
){
    suspend fun getCharacterDetail(characterId: Int): Flow<Either<CharacterErrorModel, CharacterModel?>> {
        return flow {
            val result = characterListRepository.getCharacterDetail(characterId)
            emit(result)
        }
    }
}

