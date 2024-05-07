package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.feature.character.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorDomain
import com.enriquepalmadev.domain_layer.feature.character.utils.Either
import com.enriquepalmadev.domain_layer.feature.character.utils.extensions.filterEmptyImageAndDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCharacterUseCase @Inject constructor(
    private val characterListRepository : CharacterRepository
){

    suspend fun getCharacterList(): Flow<Either<CharacterErrorDomain, List<CharacterModel>?>> {
        //Filter to empty description and image
        return flow {
            when (val response = characterListRepository.getCharacterList()) {
                is Either.Success -> {
                    val filteredList = response.data?.filterEmptyImageAndDescription()
                    emit(Either.Success(filteredList))
                }

                is Either.Error -> {
                    emit(Either.Error(response.error))
                }
            }
        }
    }
}

