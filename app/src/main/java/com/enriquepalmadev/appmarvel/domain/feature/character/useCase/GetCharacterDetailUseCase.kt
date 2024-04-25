package com.enriquepalmadev.appmarvel.domain.feature.character.useCase

import com.enriquepalmadev.appmarvel.data.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterDetailUseCase {

    private val characterListRepository= CharacterRepositoryImpl

    suspend fun getCharacterDetail(characterId: Int): Flow<Either<String, CharacterModel?>> {
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

