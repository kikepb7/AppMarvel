package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.feature.character.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorDomain
import com.enriquepalmadev.domain_layer.feature.character.utils.Either
import com.enriquepalmadev.domain_layer.feature.character.utils.extensions.filterEmptyImageAndDescriptionAndName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FiltListGetCharacterUseCase @Inject constructor(
    private val characterListRepositoryImpl : CharacterRepository
)
{
    suspend fun getCharacterFilterList(filt: String): Flow<Either<CharacterErrorDomain, List<CharacterModel>?>> {
        return flow {
            when (val response = characterListRepositoryImpl.getCharacterList()){
                is Either.Error-> {
                    emit(Either.Error(response.error))
                } //response.error
                is Either.Success-> {
                    val filteredList = response.data?.filterEmptyImageAndDescriptionAndName(filt)
                    emit(Either.Success(filteredList))
                }
            }
        }
    }
}