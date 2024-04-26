package com.enriquepalmadev.appmarvel.domain.feature.character.useCase

import com.enriquepalmadev.appmarvel.data.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.domain.feature.character.utils.extensions.filterEmptyImageAndDescriptionAndName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FiltListGetCharacterUseCase
{
    private val characterListRepositoryImpl= CharacterRepositoryImpl

    suspend fun getCharacterFilterList(filt: String): Flow<Either<CharacterError, List<CharacterModel>?>> {
        return flow {
            when (val response = characterListRepositoryImpl.getCharacterList()){
                is Either.Error -> {
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