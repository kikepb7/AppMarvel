package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.feature.character.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorDomain
import com.enriquepalmadev.domain_layer.feature.character.utils.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val characterListRepository : CharacterRepository
){
    suspend fun getCharacterDetail(characterId: Int): Flow<Either<CharacterErrorDomain, CharacterModel?>> {
        return flow {
            when (val response = characterListRepository.getCharacterDetail(characterId)){
                is Either.Success -> {
                    val character = response.data
                    emit(Either.Success(character))
                }
                is Either.Error -> {
                    emit(Either.Error(response.error))
                }
            }
        }
    }
}

