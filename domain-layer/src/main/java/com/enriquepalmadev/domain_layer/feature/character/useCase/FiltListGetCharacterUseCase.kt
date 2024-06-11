package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.domain_layer.feature.character.utils.extensions.filterByName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FiltListGetCharacterUseCase @Inject constructor(
    private val characterListRepositoryImpl : CharacterRepository
)
{
    suspend fun getCharacterFilterList(filt: String): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {
        return flow {
            when (val response = characterListRepositoryImpl.getCharacterList()){
                is Either.Error-> {
                    emit(Either.Error(response.error))
                } //response.error
                is Either.Success-> {
                    val filteredList = response.data?.filterByName(filt)
                    emit(Either.Success(filteredList))
                }
            }
        }
    }
}